package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.R;


public class MainActivity extends AppCompatActivity {
    public static int USER_ID;
    TextView tvSignUp;
    Button btnLogin;
    EditText etUserName;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSignUp = findViewById(R.id.tv_sign_up);
        btnLogin = findViewById(R.id.btn_login);
        etUserName = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        Spinner spinner = findViewById(R.id.spinner_login_types);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.login_types)
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        tvSignUp.setOnClickListener(view -> {

        });

        btnLogin.setOnClickListener(view -> {
            if (isFieldFilled(etUserName) || isFieldFilled(etPassword)) {
                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                String username = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                int userType = spinner.getSelectedItemPosition();
                USER_ID = databaseHelper.checkUser(databaseHelper.getReadableDatabase(), username, password, userType);
                if (USER_ID != -1) {
                    databaseHelper.close();
                    switch (userType) {
                        case 0:
                            startActivity(new Intent(this, AdminHomeScreenActivity.class));
                            finish();
                            break;
                        case 1:
                            finish();
                            break;
                        case 2:

                            break;
                    }
                } else {
                    etUserName.setError("");
                    etPassword.setError("");
                    findViewById(R.id.tv_error).setVisibility(View.VISIBLE);
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