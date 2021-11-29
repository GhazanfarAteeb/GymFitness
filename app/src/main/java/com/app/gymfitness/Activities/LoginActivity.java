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


public class LoginActivity extends AppCompatActivity {
    public static int USER_ID;
    TextView tvSignUp;
    Button btnLogin;
    EditText etUserName;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                finish();
            }
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
                    Intent intent;
                    switch (userType) {
                        case 0:
                            intent = new Intent(this, AdminHomeScreenActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            break;
                        case 1:
                            intent = new Intent(this, InstructorHomeScreenActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            break;
                        case 2:
                            intent = new Intent(this, MemberHomeScreenActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            break;
                    }
                } else {
                    etUserName.setError(null);
                    etPassword.setError(null);

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