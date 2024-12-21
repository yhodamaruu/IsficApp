package com.isficuniversity.isfic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isficuniversity.isfic.modules.Student;

import java.util.ArrayList;
import java.util.List;

public class ActualStudentsList extends AppCompatActivity {

    private ListView stdListView;
    private SearchView searchView;
    private DatabaseReference databaseReference;
    private ArrayAdapter<String> adapter;
    private List<Student> studentsList;
    private List<String> studentsDisplayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_students_list);


        stdListView = findViewById(R.id.stdlist);
        searchView = findViewById(R.id.search);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("2").child("data");

        studentsList = new ArrayList<>();
        studentsDisplayList = new ArrayList<>();


        loadStudentsData();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


        stdListView.setOnItemClickListener((parent, view, position, id) -> {
            showStudentDetails(studentsList.get(position));
        });
    }

    private void loadStudentsData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentsList.clear();
                studentsDisplayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Student student = snapshot.getValue(Student.class);
                    if (student != null) {
                        studentsList.add(student);
                        studentsDisplayList.add(student.getMATRICULE() + " - " + student.getPRENOM() + " " + student.getNOM());
                    }
                }


                adapter = new ArrayAdapter<>(ActualStudentsList.this, android.R.layout.simple_list_item_1, studentsDisplayList);
                stdListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ActualStudentsList.this, "Erreur de chargement des données", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterList(String query) {
        List<String> filteredList = new ArrayList<>();
        for (Student student : studentsList) {
            String fullName = student.getPRENOM() + " " + student.getNOM();
            if (fullName.toLowerCase().contains(query.toLowerCase()) || student.getMATRICULE().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(student.getMATRICULE() + " - " + fullName);
            }
        }


        adapter.clear();
        adapter.addAll(filteredList);
    }

    private void showStudentDetails(Student student) {
        BottomSheetDialog builder = new BottomSheetDialog(ActualStudentsList.this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_student_details, null);


        TextView dateNaissView = dialogView.findViewById(R.id.date_naiss);
        TextView anneeView = dialogView.findViewById(R.id.annee);
        TextView numView = dialogView.findViewById(R.id.num);
        TextView tel1View = dialogView.findViewById(R.id.tel1);
        TextView sexeView = dialogView.findViewById(R.id.sexe);
        TextView idElView = dialogView.findViewById(R.id.id_el);
        TextView nationView = dialogView.findViewById(R.id.nationnalite);

        dateNaissView.setText(student.getDATE_NAISS());
        anneeView.setText(student.getANNEE());
        numView.setText(student.getNUM());
        tel1View.setText(student.getTEL1());
        sexeView.setText(student.getSexe());
        idElView.setText(student.getID_EL());
        nationView.setText(student.getNATIONALITE());

        builder.setContentView(dialogView);
        builder.show();
    }
}
