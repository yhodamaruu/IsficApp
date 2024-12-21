package com.isficuniversity.isfic.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.isficuniversity.isfic.LoginStdActivity;
import com.isficuniversity.isfic.R;
import com.isficuniversity.isfic.SIgnUpActivity;

public class AdminPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.show_bottomnav).setOnClickListener(view -> {
            BottomSheetDialog builder = new BottomSheetDialog(AdminPageActivity.this);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_st, null);
            builder.setContentView(dialogView);

            dialogView.findViewById(R.id.code_abus).setOnClickListener(v -> {
                Intent code_i = new Intent(AdminPageActivity.this, PasswordGenActivity.class);
                startActivity(code_i);
            });
            dialogView.findViewById(R.id.btn_password).setOnClickListener(v -> {
                Intent pss_i = new Intent(AdminPageActivity.this, ChangepssActivity.class);
                startActivity(pss_i);
            });

            builder.show();
        });


    }
}