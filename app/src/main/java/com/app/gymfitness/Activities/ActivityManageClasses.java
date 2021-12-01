package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.app.gymfitness.Adapters.ClassAdapter;
import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.Models.Class;
import com.app.gymfitness.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityManageClasses extends AppCompatActivity {
    EditText etSearch;
    List<Class> myClass;
    static String instructorName;
    RecyclerView recyclerView;
    ClassAdapter classAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_classes);
        etSearch = findViewById(R.id.et_search);
        recyclerView = findViewById(R.id.recycler_view);

        myClass = new ArrayList<>();

        classAdapter = new ClassAdapter(this);
        // FETCHING THE INSTRUCTOR NAME FIRST
        setAdapterData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(classAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // DATABASE QUERY HERE
                if (etSearch.getText().toString().isEmpty()) {
                    setAdapterData();
                }
                else {
                    DatabaseHelper databaseHelper = new DatabaseHelper(ActivityManageClasses.this);
                    classAdapter.setData(databaseHelper.getSearchedData(databaseHelper.getReadableDatabase(), etSearch.getText().toString().trim()));
                }

            }
        });

        findViewById(R.id.fab).setOnClickListener(view -> startActivity(new Intent(ActivityManageClasses.this, ActivityAddClass.class)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, InstructorHomeScreenActivity.class));
        finish();
    }

    public void setAdapterData() {
        myClass.clear();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursorInstructorInfo = databaseHelper.getInstructorName(databaseHelper.getReadableDatabase(), LoginActivity.USER_ID);
        while (cursorInstructorInfo.moveToNext()) {
            int index = cursorInstructorInfo.getColumnIndex(DatabaseHelper.USER_NAME);
            instructorName = cursorInstructorInfo.getString(index);
        }

        //FETCHING THE CLASSES DETAILS OF THE INSTRUCTOR
        Cursor cursorClassInfo = databaseHelper.getAllClasses(databaseHelper.getReadableDatabase(), LoginActivity.USER_ID);

        // ADDING CLASS INFORMATION TO THE MODEL CLASS LIST
        while (cursorClassInfo.moveToNext()) {
            int idIndex = cursorClassInfo.getColumnIndex(DatabaseHelper.CLASS_ID);
            int classCapacityIndex = cursorClassInfo.getColumnIndex(DatabaseHelper.CLASS_CAPACITY);
            int dayIdIndex = cursorClassInfo.getColumnIndex(DatabaseHelper.CLASS_DAY_ID);
            int classStartTimeIndex = cursorClassInfo.getColumnIndex(DatabaseHelper.CLASS_START_TIME);
            int classEndTimeIndex = cursorClassInfo.getColumnIndex(DatabaseHelper.CLASS_END_TIME);
            int classDifficultyIndex = cursorClassInfo.getColumnIndex(DatabaseHelper.CLASS_DIFFICULTY);
            int classTypeIdIndex = cursorClassInfo.getColumnIndex(DatabaseHelper.CLASS_TYPE_ID);

            // FETCHING THE CLASS TYPE NAME AND CLASS TYPE DESCRIPTION
            Cursor classTypeInfo = databaseHelper.getInstructorClassTypesDetails(databaseHelper.getReadableDatabase(), cursorClassInfo.getInt(classTypeIdIndex));

            String className = "";
            String classDescription = "";

            // ASSIGNING THE CLASS NAME AND CLASS DESCRIPTION TO THE VARIABLES
            if (classTypeInfo.moveToFirst()) {
                int classNameIndex = classTypeInfo.getColumnIndex(DatabaseHelper.TYPE_NAME);
                int classDescriptionIndex = classTypeInfo.getColumnIndex(DatabaseHelper.TYPE_DESCRIPTION);
                className = classTypeInfo.getString(classNameIndex);
                classDescription = classTypeInfo.getString(classDescriptionIndex);
            }
            classTypeInfo.close();
            // ADDING DATA TO THE CLASS LIST
            myClass.add(
                    new Class(
                            cursorClassInfo.getInt(idIndex),
                            LoginActivity.USER_ID,
                            cursorClassInfo.getInt(classCapacityIndex),
                            cursorClassInfo.getInt(dayIdIndex),
                            cursorClassInfo.getString(classStartTimeIndex),
                            cursorClassInfo.getString(classEndTimeIndex),
                            cursorClassInfo.getString(classDifficultyIndex),
                            instructorName,
                            className,
                            classDescription
                    )
            );
        }
        cursorClassInfo.close();
        classAdapter.setData(myClass);
    }
}