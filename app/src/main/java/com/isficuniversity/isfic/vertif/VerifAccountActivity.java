package com.isficuniversity.isfic.vertif;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.isficuniversity.isfic.MainActivity;
import com.isficuniversity.isfic.R;
import com.isficuniversity.isfic.splash.SplashScreen;

public class VerifAccountActivity extends AppCompatActivity {
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verif_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        handler = new Handler();
        handler.postDelayed(this::justIntent, 3000);

    }

    private void justIntent() {

        Intent gotoMain = new Intent(VerifAccountActivity.this, MainActivity.class);
        startActivity(gotoMain);
        finish();

    }
}