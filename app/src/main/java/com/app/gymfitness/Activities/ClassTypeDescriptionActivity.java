package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.Dialogs.UpdateClassTypeDialog;
import com.app.gymfitness.R;

public class ClassTypeDescriptionActivity extends AppCompatActivity {
    Bundle data;
    TextView tvClassName, tvClassDescription;
    Button btnUpdate, btnDelete;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_type_description);
        tvClassName = findViewById(R.id.tv_class_type_name);
        tvClassDescription = findViewById(R.id.tv_class_type_description);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
        findViewById(R.id.iv_back).setOnClickListener(view -> startActivity(new Intent(this, ManageTypesActivity.class)));

        data = getIntent().getBundleExtra("ClassType");
        tvClassName.setText(data.getString("ClassTypeName"));
        tvClassDescription.setText(data.getString("ClassTypeDescription"));

        databaseHelper = new DatabaseHelper(this);
        UpdateClassTypeDialog.UpdateDataListener listener = () -> {
            startActivity(new Intent(ClassTypeDescriptionActivity.this, ManageTypesActivity.class));
            finish();
        };
        btnUpdate.setOnClickListener(view -> {
            UpdateClassTypeDialog updateClassTypeDialog = new UpdateClassTypeDialog();
            updateClassTypeDialog.setArguments(data);
            updateClassTypeDialog.show(getSupportFragmentManager(), "ClassType");
            updateClassTypeDialog.setUpdateDataListener(listener);
        });

        btnDelete.setOnClickListener(view -> {
            databaseHelper.deleteClassType(databaseHelper.getReadableDatabase(), data.getInt("ClassTypeID"));
            startActivity(new Intent(this, ManageTypesActivity.class));
            finish();
        });
    }

}