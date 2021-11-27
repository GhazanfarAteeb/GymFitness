package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.app.gymfitness.Adapters.TypesAdapter;
import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.Dialogs.AddClassTypeDialog;
import com.app.gymfitness.Models.ClassType;
import com.app.gymfitness.R;

import java.util.ArrayList;
import java.util.List;

public class ManageTypesActivity extends AppCompatActivity {
    RecyclerView recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_types);

        recyclerview = findViewById(R.id.recycler_view);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllClassTypes(databaseHelper.getReadableDatabase());

        List<ClassType> classTypeList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idColIndex = cursor.getColumnIndex(DatabaseHelper.CLASS_TYPE_ID);
            int classTypeNameColIndex = cursor.getColumnIndex(DatabaseHelper.TYPE_NAME);
            int classTypeDescriptionColIndex = cursor.getColumnIndex(DatabaseHelper.TYPE_DESCRIPTION);

            classTypeList.add(
              new ClassType(cursor.getInt(idColIndex), cursor.getString(classTypeNameColIndex), cursor.getString(classTypeDescriptionColIndex))
            );
        }
        TypesAdapter adapter = new TypesAdapter(this);
        adapter.setData(classTypeList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);

        findViewById(R.id.fab).setOnClickListener(view -> {
            AddClassTypeDialog addClassType =new AddClassTypeDialog();
            addClassType.show(getSupportFragmentManager(),"Add type data");
        });
    }
}