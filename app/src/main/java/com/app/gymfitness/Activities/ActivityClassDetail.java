package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.Models.Class;
import com.app.gymfitness.R;


public class ActivityClassDetail extends AppCompatActivity {

    Class myClass;
    Button viewEnrolledMembers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewEnrolledMembers = findViewById(R.id.btn_view_members);

        setContentView(R.layout.activity_class_detail);
        myClass = (Class) getIntent().getSerializableExtra("class");
        String[] dayList = getResources().getStringArray(R.array.days);
        viewEnrolledMembers = findViewById(R.id.btn_view_members);

        ((TextView) findViewById(R.id.tv_class_type_name)).setText(myClass.getClassName());
        ((TextView) findViewById(R.id.tv_class_type_description)).setText(myClass.getDescription());
        ((TextView) findViewById(R.id.tv_instructor_name)).setText(myClass.getInstructorName());
        ((TextView) findViewById(R.id.tv_difficulty)).setText(myClass.getDifficulty());
        ((TextView) findViewById(R.id.tv_time)).setText(myClass.getStartTime() + " - " + myClass.getEndTIme());
        ((TextView) findViewById(R.id.tv_day)).setText(dayList[myClass.getDayId()]);
        ((TextView) findViewById(R.id.tv_capacity)).setText(String.valueOf(myClass.getCapacity()));
        findViewById(R.id.iv_back).setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityManageClasses.class);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.btn_view_members).setOnClickListener(view -> {
           Intent intent = new Intent(this, ActivityEnrolledMembers.class);
           intent.putExtra("class", myClass);
           startActivity(intent);
        });
        findViewById(R.id.btn_delete).setOnClickListener(view -> {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.deleteClassAndEnrollmentRecord(databaseHelper.getReadableDatabase(), myClass.getId());
            Intent intent = new Intent(this, ActivityManageClasses.class);
            startActivity(intent);
            finish();
        });

        if (myClass.getInstructorId() != LoginActivity.USER_ID) {
            LinearLayout ll = findViewById(R.id.bottom);
            ll.setVisibility(View.GONE);
        }
    }
}