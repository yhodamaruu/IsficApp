package com.isficuniversity.isfic.admin;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isficuniversity.isfic.R;
import com.isficuniversity.isfic.adaptaters.CodeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PasswordGenActivity extends AppCompatActivity {

    private Button generateCodeBtn;
    private ListView accessCodeList;
    private DatabaseReference databaseRef;
    private ArrayList<String> codeList;
    private CodeAdapter codeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_gen);

        generateCodeBtn = findViewById(R.id.generate_code_btn);
        accessCodeList = findViewById(R.id.access_code_list);

        databaseRef = FirebaseDatabase.getInstance().getReference("access_code");

        codeList = new ArrayList<>();
        codeAdapter = new CodeAdapter(this, codeList);
        accessCodeList.setAdapter(codeAdapter);
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadCodesFromFirebase();

        generateCodeBtn.setOnClickListener(v -> showProgressDialog());
    }

    private void loadCodesFromFirebase() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                codeList.clear();
                for (DataSnapshot codeSnapshot : snapshot.getChildren()) {
                    String code = codeSnapshot.getValue(String.class);
                    if (code != null) {
                        String key = codeSnapshot.getKey();
                        codeList.add(code + " --" +  key );
                    }
                }
                codeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PasswordGenActivity.this, "Erreur de chargement des codes.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressDialog() {
        Dialog progressDialog = new Dialog(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.progress_dialog, null);
        progressDialog.setContentView(dialogView);
        progressDialog.setCancelable(false);

        TextView codePreview = dialogView.findViewById(R.id.progress_code_preview);
        progressDialog.show();

        Handler handler = new Handler();
        Runnable codeAnimation = new Runnable() {
            int counter = 0;

            @Override
            public void run() {
                if (counter < 6) {
                    codePreview.setText(generateRandomCode());
                    handler.postDelayed(this, 100);
                    counter++;
                } else {
                    progressDialog.dismiss();
                    generateAndStoreCode();
                }
            }
        };
        handler.post(codeAnimation);
    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }

    private void generateAndStoreCode() {
        String newCode = generateRandomCode();

        String pushId = databaseRef.push().getKey();
        if (pushId != null) {
            Map<String, Object> codeData = new HashMap<>();
            codeData.put(pushId, newCode);
            databaseRef.updateChildren(codeData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Code généré avec succès !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Erreur lors de la génération du code.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Erreur lors de la génération de l'identifiant Firebase.", Toast.LENGTH_SHORT).show();
        }
    }
}
