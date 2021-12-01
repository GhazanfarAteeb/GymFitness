package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.Models.Class;
import com.app.gymfitness.R;

public class ClassMemberEnrollmentDescription extends AppCompatActivity {
    Class myClass;
    Button viewEnrolledMembers;
    Button btnUnroll;
    Button btnEnroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_member_enrollment_description);
        viewEnrolledMembers = findViewById(R.id.btn_view_members);
        btnEnroll = findViewById(R.id.btn_enroll);
        btnUnroll = findViewById(R.id.btn_unroll);

        myClass = (Class) getIntent().getSerializableExtra("class");
        String[] dayList = getResources().getStringArray(R.array.days);
        viewEnrolledMembers = findViewById(R.id.btn_view_members);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        ((TextView) findViewById(R.id.tv_class_type_name)).setText(myClass.getClassName());
        ((TextView) findViewById(R.id.tv_class_type_description)).setText(myClass.getDescription());
        ((TextView) findViewById(R.id.tv_instructor_name)).setText(myClass.getInstructorName());
        ((TextView) findViewById(R.id.tv_difficulty)).setText(myClass.getDifficulty());
        ((TextView) findViewById(R.id.tv_time)).setText(myClass.getStartTime() + " - " + myClass.getEndTIme());
        ((TextView) findViewById(R.id.tv_day)).setText(dayList[myClass.getDayId()]);
        ((TextView) findViewById(R.id.tv_capacity)).setText(String.valueOf(myClass.getCapacity()));

        findViewById(R.id.iv_back).setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityMemberClasses.class);
            startActivity(intent);
        });

        btnEnroll.setOnClickListener(view -> {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.ENROLLMENT_USER_ID, LoginActivity.USER_ID);
            values.put(DatabaseHelper.ENROLLMENT_CLASS_ID, myClass.getId());
            databaseHelper.getWritableDatabase().insert(DatabaseHelper.ENROLLMENT_TABLE_NAME, null, values);
            Intent intent = new Intent(this, ActivityMemberClasses.class);
            startActivity(intent);
        });
        btnUnroll.setOnClickListener(view -> {
            databaseHelper.unrollMember(databaseHelper.getReadableDatabase(), myClass.getId(), LoginActivity.USER_ID);
            Intent intent = new Intent(this, ActivityMemberClasses.class);
            startActivity(intent);
            finish();
        });

        if (databaseHelper.canUserRegister(databaseHelper.getReadableDatabase(), myClass.getId(), myClass.getCapacity())) {
            if (databaseHelper.isMemberEnrolled(databaseHelper.getReadableDatabase(), myClass.getId(),LoginActivity.USER_ID)) {
                btnEnroll.setVisibility(View.GONE);
                btnUnroll.setVisibility(View.VISIBLE);
            }
            else {
                btnUnroll.setVisibility(View.GONE);
                btnEnroll.setVisibility(View.VISIBLE);
            }
        } else {
            btnUnroll.setVisibility(View.GONE);
            btnEnroll.setVisibility(View.VISIBLE);
            btnEnroll.setEnabled(false);
            btnEnroll.setText("Class capacity is filled!");
        }
    }
}