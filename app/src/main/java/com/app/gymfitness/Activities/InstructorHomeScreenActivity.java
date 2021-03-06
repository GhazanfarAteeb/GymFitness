package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.R;

public class InstructorHomeScreenActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_home_screen);

        context = this;
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        String userName = databaseHelper.getUsername(databaseHelper.getReadableDatabase(), LoginActivity.USER_ID);
        databaseHelper.close();
        ((TextView)findViewById(R.id.tv_name)).setText(userName);
        findViewById(R.id.btn_manage_classes).setOnClickListener(view-> startActivity(new Intent(this, ActivityManageClasses.class)));
        findViewById(R.id.btn_logout).setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }

    

}