package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.gymfitness.Adapters.MemberClassAdapter;
import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.Models.Class;
import com.app.gymfitness.R;

import java.util.List;

public class ActivityMemberClasses extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText etSearch;
    Spinner spinnerDayOfWeek;
    DatabaseHelper databaseHelper;
    List<Class> classMemberList;
    MemberClassAdapter memberClassAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_classes);
        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recycler_view);
        etSearch = findViewById(R.id.et_search);
        spinnerDayOfWeek = findViewById(R.id.spinner_select_day);

        String[] dayList = getResources().getStringArray(R.array.spinner_filer);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                dayList
        );

        spinnerDayOfWeek.setAdapter(dayAdapter);

        memberClassAdapter = new MemberClassAdapter(this);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setAdapter(memberClassAdapter);
        classMemberList = databaseHelper.getMemberClassList(databaseHelper.getReadableDatabase(), LoginActivity.USER_ID);
        memberClassAdapter.setData(classMemberList);

        etSearch.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                classMemberList.clear();
                if (etSearch.getText().toString().isEmpty()) {
                    classMemberList = databaseHelper.getMemberClassList(databaseHelper.getReadableDatabase(), LoginActivity.USER_ID);
                } else {
                    classMemberList = databaseHelper.getMemberSearchData(
                            databaseHelper.getReadableDatabase(),
                            etSearch.getText().toString(),
                            spinnerDayOfWeek.getSelectedItemPosition()

                    );
                }
                memberClassAdapter.setData(classMemberList);
            }
        });

        spinnerDayOfWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classMemberList.clear();
                if (etSearch.getText().toString().isEmpty()) {
                    classMemberList = databaseHelper.getMemberClassList(databaseHelper.getReadableDatabase(), LoginActivity.USER_ID);
                } else {
                    classMemberList = databaseHelper.getMemberSearchData(
                            databaseHelper.getReadableDatabase(),
                            etSearch.getText().toString(),
                            i

                    );
                }
                memberClassAdapter.setData(classMemberList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}