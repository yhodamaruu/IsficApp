package com.isficuniversity.isfic.utils.cv.samples;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.isficuniversity.isfic.R;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SampleCVActivity extends AppCompatActivity {

    private LinearLayout dynamicFieldsLayout;
    private Button btnAddField, btnGenerateCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_cvactivity);

        dynamicFieldsLayout = findViewById(R.id.dynamicFieldsLayout);
        btnAddField = findViewById(R.id.btnAddField);
        btnGenerateCV = findViewById(R.id.btnGenerateCV);


        btnAddField.setOnClickListener(v -> addNewField());


        btnGenerateCV.setOnClickListener(v -> generatePDF());
    }


    private void addNewField() {
        EditText editText = new EditText(this);
        editText.setHint("Entrez votre information ici");
        editText.setPadding(10, 10, 10, 10);
        dynamicFieldsLayout.addView(editText);
    }


    private void generatePDF() {

        File downloadsDirectory = new File(getExternalFilesDir(null).getParent(), "Download");
        if (!downloadsDirectory.exists()) {
            downloadsDirectory.mkdirs();
        }

        File file = new File(downloadsDirectory, "CV_Genere.pdf");

        try {

            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);


            document.add(new Paragraph("Nom: " + getTextFromField(R.id.nameField)).setTextAlignment(TextAlignment.LEFT).setMarginBottom(10));
            document.add(new Paragraph("Email: " + getTextFromField(R.id.emailField)).setTextAlignment(TextAlignment.LEFT).setMarginBottom(10));


            for (int i = 0; i < dynamicFieldsLayout.getChildCount(); i++) {
                EditText field = (EditText) dynamicFieldsLayout.getChildAt(i);
                if (!field.getText().toString().isEmpty()) {

                    document.add(new Paragraph(field.getText().toString()).setTextAlignment(TextAlignment.CENTER).setMarginBottom(10));
                }
            }

            document.close();
            Toast.makeText(this, "CV généré avec succès! Le fichier est dans : " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la génération du CV", Toast.LENGTH_SHORT).show();
        }
    }


    private String getTextFromField(int fieldId) {
        EditText editText = findViewById(fieldId);
        return editText != null ? editText.getText().toString() : "";
    }
}
