package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.gymfitness.Models.Class;
import com.app.gymfitness.R;

public class ActivityClassDetail extends AppCompatActivity {

    Class myClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        myClass = (Class) getIntent().getSerializableExtra("class");
    }
}