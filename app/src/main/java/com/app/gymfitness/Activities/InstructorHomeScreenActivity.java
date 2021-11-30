package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.gymfitness.R;

public class InstructorHomeScreenActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_home_screen);

        context = this;

        findViewById(R.id.btn_manage_classes).setOnClickListener(view-> startActivity(new Intent(this, ActivityManageClasses.class)));
        findViewById(R.id.btn_logout).setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }

}