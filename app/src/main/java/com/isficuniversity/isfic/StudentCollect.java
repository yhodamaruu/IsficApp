

package com.isficuniversity.isfic;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentCollect extends AppCompatActivity {

    private static final String PREFS_NAME = "StudentPrefs";
    private static final String KEY_STUDENTS = "students";

    private ListView studentListView;
    private ArrayAdapter<String> adapter;
    private List<String> studentList = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_collect);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        studentListView = findViewById(R.id.student_list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentListView.setAdapter(adapter);

        loadStudents();

        findViewById(R.id.add_btn).setOnClickListener(v -> showAddStudentDialog());
        findViewById(R.id.back_btn).setOnClickListener(v -> finish());

        findViewById(R.id.send_btn).setOnClickListener(v -> {
            EditText emailField = findViewById(R.id.email_field);
            String email = emailField.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Veuillez entrer une adresse e-mail", Toast.LENGTH_SHORT).show();
            } else {
                generatePdfAndSendEmail(email);
            }
        });
    }

    private void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_student, null);
        builder.setView(dialogView);

        EditText nameField = dialogView.findViewById(R.id.name_field);
        EditText emailField = dialogView.findViewById(R.id.email_field);
        EditText contactField = dialogView.findViewById(R.id.contact_field);
        EditText currentMajorField = dialogView.findViewById(R.id.current_major_field);
        EditText desiredMajorField = dialogView.findViewById(R.id.desired_major_field);

        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            String studentInfo = "Nom: " + nameField.getText().toString().trim() +
                    "\nEmail: " + emailField.getText().toString().trim() +
                    "\nContact: " + contactField.getText().toString().trim() +
                    "\nFilière actuelle: " + currentMajorField.getText().toString().trim() +
                    "\nFilière souhaitée: " + desiredMajorField.getText().toString().trim();

            if (!TextUtils.isEmpty(nameField.getText()) && !TextUtils.isEmpty(emailField.getText())) {
                studentList.add(studentInfo);
                adapter.notifyDataSetChanged();
                saveStudents();
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Annuler", null);
        builder.create().show();
    }

    private void loadStudents() {
        Set<String> studentSet = sharedPreferences.getStringSet(KEY_STUDENTS, new HashSet<>());
        studentList.clear();
        studentList.addAll(studentSet);
        adapter.notifyDataSetChanged();
    }

    private void saveStudents() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(KEY_STUDENTS, new HashSet<>(studentList));
        editor.apply();
    }

    private void generatePdfAndSendEmail(String email) {
        try {

            File pdfFile = new File(getExternalFilesDir(null), "student_list.pdf");
            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFile));
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdfDocument);


            Paragraph title = new Paragraph("Liste des élèves StudentScollect")
                    .setUnderline()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);


            String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            Paragraph date = new Paragraph("Date de création : " + currentDate)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(date);


            float[] columnWidths = {150, 150, 100, 150, 150};
            Table table = new Table(columnWidths);


            table.addHeaderCell(new Cell().add(new Paragraph("Nom")));
            table.addHeaderCell(new Cell().add(new Paragraph("Email")));
            table.addHeaderCell(new Cell().add(new Paragraph("Contact")));
            table.addHeaderCell(new Cell().add(new Paragraph("Filière Actuelle")));
            table.addHeaderCell(new Cell().add(new Paragraph("Filière Souhaitée")));


            for (String studentInfo : studentList) {
                String[] details = studentInfo.split("\n");
                table.addCell(new Cell().add(new Paragraph(details[0].replace("Nom: ", ""))));
                table.addCell(new Cell().add(new Paragraph(details[1].replace("Email: ", ""))));
                table.addCell(new Cell().add(new Paragraph(details[2].replace("Contact: ", ""))));
                table.addCell(new Cell().add(new Paragraph(details[3].replace("Filière actuelle: ", ""))));
                table.addCell(new Cell().add(new Paragraph(details[4].replace("Filière souhaitée: ", ""))));
            }


            document.add(table);


            document.close();


            android.net.Uri pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", pdfFile);


            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("application/pdf");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Liste des étudiants");
            emailIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(emailIntent, "Envoyer l'e-mail avec"));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la génération du PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
