package com.isficuniversity.isfic;

import static com.google.gson.internal.$Gson$Types.arrayOf;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.isficuniversity.isfic.splash.SplashScreen;
import com.isficuniversity.isfic.vertif.VerifAccountActivity;

public class LoginStdActivity extends AppCompatActivity {
    private Button loginBtn;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private EditText emailText, passwordTxt;
    private static final int PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_std);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn = findViewById(R.id.loginbtn);
        emailText = findViewById(R.id.emailtxt);
        passwordTxt = findViewById(R.id.passwordtxt);
        loginBtn.setOnClickListener(v -> handleLogin());
        if (!checkPermissions()) {
            requestPermissions();
        }

        findViewById(R.id.more_option).setOnClickListener(view -> {
            BottomSheetDialog builder = new BottomSheetDialog(LoginStdActivity.this);
            View dialogView = getLayoutInflater().inflate(R.layout.layout_not_login, null);
            builder.setContentView(dialogView);

            dialogView.
                    findViewById(R.id.create_account).setOnClickListener(v -> {
                        Intent sign_i = new Intent(LoginStdActivity.this, SIgnUpActivity.class);
                        startActivity(sign_i);
                    });

            builder.show();
        });


    }

    private boolean checkPermissions() {

        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {

        ActivityCompat.requestPermissions(this,
                new String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.RECORD_AUDIO,
                        android.Manifest.permission.CALL_PHONE,
                },
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean allPermissionsGranted = true;
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        allPermissionsGranted = false;
                        break;
                    }
                }
                if (allPermissionsGranted) {
                    //test
                } else {
                    //test1
                }
            }
        }
    }

    private void handleLogin() {
        String email = emailText.getText().toString();
        String password = passwordTxt.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailText.setError("Veuillez entrer votre identifiant.");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Adresse email invalide.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordTxt.setError("Veuillez entrer votre mot de passe.");
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        handleSuccessfulLogin();
                    } else {
                        Toast.makeText(LoginStdActivity.this, "Échec de la connexion. Vérifiez vos identifiants.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleSuccessfulLogin() {
    Intent gotoVERIF = new Intent(LoginStdActivity.this, VerifAccountActivity.class);
        startActivity(gotoVERIF);
        finish();
    }

}