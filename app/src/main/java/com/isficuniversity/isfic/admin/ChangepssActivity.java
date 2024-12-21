package com.isficuniversity.isfic.admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.isficuniversity.isfic.R;

public class ChangepssActivity extends AppCompatActivity {

    private EditText newPassword, newPassworda;
    private Button changePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_changepss);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        newPassword = findViewById(R.id.old_password);
        newPassworda = findViewById(R.id.new_password);
        changePasswordBtn = findViewById(R.id.change_password_btn);

        findViewById(R.id.back_btn).setOnClickListener(v -> {
            finish();
        });
        changePasswordBtn.setOnClickListener(view -> {
            String newPass = newPassword.getText().toString();
            String newPassa = newPassworda.getText().toString();

            if (newPass.isEmpty() || newPassa.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newPass.equals(newPassa)) {
                new AlertDialog.Builder(this)
                        .setTitle("Erreur")
                        .setMessage("Les mots de passe ne sont pas identique")
                        .setPositiveButton("Reprendre", (dialog, which) -> dialog.dismiss())
                        .show();
                return;
            }


            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                            .setMessage("Êtes-vous sûr de vouloir changer le mot de passe ?")
                                    .setPositiveButton("Oui", (dialog, which) -> updatePassword(newPass))
                                            .setNegativeButton("Non", null)
                                                    .show();
        });
    }

    private void updatePassword(String newPass) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Modification en cours...");
                progressDialog.setCancelable(false);
        progressDialog.show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("settings").child("Security").child("adminPassword");

                        databaseReference.setValue(newPass).addOnCompleteListener(task -> {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Mot de passe changé avec succès", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Erreur lors de la modification", Toast.LENGTH_SHORT).show();
                            }
                        });
    }
}
