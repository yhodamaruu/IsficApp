package com.isficuniversity.isfic.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.isficuniversity.isfic.LoginStdActivity;
import com.isficuniversity.isfic.MainActivity;
import com.isficuniversity.isfic.R;

public class SplashScreen extends AppCompatActivity {
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        handler = new Handler();
        handler.postDelayed(this::checkUser, 3000); // Délai avant la vérification des permissions

    }

    private void checkUser() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        boolean isUserLoggedIn = firebaseAuth.getCurrentUser() != null;

        if (isUserLoggedIn) {
            Intent gotoMain = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(gotoMain);
            finish();
        }else{
            Intent gotoLogin = new Intent(SplashScreen.this, LoginStdActivity.class);
            startActivity(gotoLogin);
            finish();
        }

    }
}