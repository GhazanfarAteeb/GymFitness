package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.gymfitness.R;

public class ActivityAddClass extends AppCompatActivity {

    EditText etStartTime,etEndTime,etCapacity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        etEndTime = findViewById(R.id.et_end_time);
        etStartTime = findViewById(R.id.et_start_time);
        etCapacity = findViewById(R.id.et_capacity);
    }

    private void setListeners(){
        etEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}