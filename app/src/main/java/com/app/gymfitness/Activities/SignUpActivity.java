package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.R;

public class SignUpActivity extends AppCompatActivity {
    RadioGroup rg;
    EditText etName, etEmail, etPassword;
    RadioButton rbMale, rbFemale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        rg = findViewById(R.id.rg_gender);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        Spinner spinner = findViewById(R.id.spinner_login_types);
        rbFemale = findViewById(R.id.rb_female);
        rbMale = findViewById(R.id.rb_male);
        rbMale.setChecked(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.sign_up_types)
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        findViewById(R.id.tv_sign_in).setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));

        findViewById(R.id.btn_sign_up).setOnClickListener(view ->{
            boolean passwordFilled = isFieldFilled(etPassword);
            boolean emailFilled = isFieldFilled(etEmail);
            boolean nameFilled =  isFieldFilled(etName);

            if (nameFilled && emailFilled && passwordFilled) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.USER_NAME, etName.getText().toString().trim());
                contentValues.put(DatabaseHelper.USER_EMAIL, etEmail.getText().toString().trim());
                contentValues.put(DatabaseHelper.USER_PASSWORD, etPassword.getText().toString().trim());
                /*   IN REGISTER, THE SPINNER FIRST MOST POSITION IS FOR INSTRUCTOR BUT ACCORDING TO
                     THE DESIGNED DATABASE THE ADMIN WILL HAVE THE FIRST MOST POSITION WHILE THE
                     INSTRUCTOR WILL HAVE SECOND POSITION AND MEMBER WILL HAVE LAST POSITION
                 */
                if(spinner.getSelectedItemPosition() == 0)
                    contentValues.put(DatabaseHelper.USER_TYPE_ID, 1);
                else
                    contentValues.put(DatabaseHelper.USER_TYPE_ID, 2);
                /*
                       ACCORDING TO THE DATABASE THE MALE GENDER HAVE ID 1 WHILE FEMALE GENDER WILL
                       HAVE ID 2
                 */
                if (rg.getCheckedRadioButtonId() == 0)
                    contentValues.put(DatabaseHelper.USER_GENDER, 1);
                else
                    contentValues.put(DatabaseHelper.USER_GENDER, 2);

                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                databaseHelper.getWritableDatabase().insert(DatabaseHelper.USER_TABLE_NAME, null, contentValues);
                databaseHelper.close();
                switch(spinner.getSelectedItemPosition()) {
                    case 0:
                        startActivity(new Intent(this, InstructorHomeScreenActivity.class));
                        finish();
                        break;
                    case 1:
                        startActivity(new Intent(this, MemberHomeScreenActivity.class));
                        break;
                }
            }
        });
    }
    private boolean isFieldFilled(EditText field) {
        boolean fieldFilled = true;

        if (field.getText().toString().trim().isEmpty()) {
            field.setError("Required field cannot be empty.");
            fieldFilled = false;
        }

        return fieldFilled;
    }
}