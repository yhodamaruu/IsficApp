package com.isficuniversity.isfic.utils.cv;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.isficuniversity.isfic.R;
import com.isficuniversity.isfic.utils.cv.samples.SampleCVActivity;

public class CreateCVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_cvactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.back_btn).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.sample_format).setOnClickListener(v -> {
            Intent sample_i = new Intent(CreateCVActivity.this, SampleCVActivity.class);
            startActivity(sample_i);
        });
    }
}