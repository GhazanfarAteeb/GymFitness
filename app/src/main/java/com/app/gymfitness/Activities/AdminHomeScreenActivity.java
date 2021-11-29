package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.app.gymfitness.R;

public class AdminHomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);
        findViewById(R.id.btn_manage_users).setOnClickListener(view -> startActivity(new Intent(this, ManageUsersActivity.class)));
        findViewById(R.id.btn_manage_classes).setOnClickListener(view-> startActivity(new Intent(this, ManageTypesActivity.class)));
        findViewById(R.id.btn_logout).setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}