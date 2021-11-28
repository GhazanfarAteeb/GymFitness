package com.app.gymfitness.Dialogs;


import android.app.Dialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.R;

public class AddClassTypeDialog extends AppCompatDialogFragment {
    UpdateDataListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_class_type, null);
        EditText etTypeName = view.findViewById(R.id.et_type_name);
        EditText etDescription = view.findViewById(R.id.et_type_description);
        builder.setView(view)
                .setTitle("Add data")
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .setPositiveButton("Add", (dialog, which) -> {
                    boolean typeNameFilled = isFieldFilled(etTypeName);
                    boolean descriptionFilled = isFieldFilled(etDescription);
                    if (typeNameFilled && descriptionFilled) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
                        ContentValues content = new ContentValues();
                        content.put(DatabaseHelper.TYPE_NAME, etTypeName.getText().toString());
                        content.put(DatabaseHelper.TYPE_DESCRIPTION, etDescription.getText().toString());
                        databaseHelper.getWritableDatabase().insert(DatabaseHelper.TYPE_TABLE_NAME, null, content);
                        Toast.makeText(getActivity().getApplicationContext(), "Type added successfully", Toast.LENGTH_SHORT).show();
                        listener.UpdateRecyclerVIewData();

                    }
                });
        return builder.create();
    }

    private boolean isFieldFilled(EditText field) {
        boolean fieldFilled = true;

        if (field.getText().toString().trim().isEmpty()) {
            field.setError("Required field cannot be empty.");
            fieldFilled = false;
        }

        return fieldFilled;
    }

    public void setUpdateDataListener(UpdateDataListener listener) {
        this.listener = listener;
    }

    public interface UpdateDataListener {
        void UpdateRecyclerVIewData();
    }
}
