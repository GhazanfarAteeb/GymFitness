package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
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

        findViewById(R.id.tv_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        });

        findViewById(R.id.btn_sign_up).setOnClickListener(view ->{
            boolean passwordFilled = isFieldFilled(etPassword);
            boolean emailFilled = isFieldFilled(etEmail);
            boolean nameFilled =  isFieldFilled(etName);

            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            if (nameFilled && emailFilled && passwordFilled) {
                if (!databaseHelper.checkExistingUser(databaseHelper.getReadableDatabase(),etEmail.getText().toString().trim())) {
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


                    databaseHelper.getWritableDatabase().insert(DatabaseHelper.USER_TABLE_NAME, null, contentValues);
                    LoginActivity.USER_ID = databaseHelper.checkUser(
                            databaseHelper.getReadableDatabase(),
                            etEmail.getText().toString().trim(),
                            etPassword.getText().toString().trim(),
                            spinner.getSelectedItemPosition()+1
                    );
                    databaseHelper.close();
                    Intent intent;
                    int position = spinner.getSelectedItemPosition();
                    switch(position) {
                        case 0:
                            intent = new Intent(this, InstructorHomeScreenActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            break;
                        case 1:
                            intent = new Intent(this, MemberHomeScreenActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            break;
                    }
                }
                else {
                    etEmail.setError("Username already exists");
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