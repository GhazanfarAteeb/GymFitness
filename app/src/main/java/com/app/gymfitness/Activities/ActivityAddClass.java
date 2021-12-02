package com.app.gymfitness.Activities;

import static com.app.gymfitness.Activities.LoginActivity.USER_ID;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.Models.ClassType;
import com.app.gymfitness.R;
import com.app.gymfitness.utils.MyUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ActivityAddClass extends AppCompatActivity {
    Spinner spinnerClassTypes;
    Spinner spinnerGetDay;
    EditText etStartTime, etEndTime, etCapacity;
    ArrayList<String> classTypesNameList;
    ArrayList<ClassType> classTypesList;
    Spinner spinnerGetDifficulty;
    Button btnSaveClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        btnSaveClass = findViewById(R.id.btn_save_class);
        etEndTime = findViewById(R.id.et_end_time);
        etStartTime = findViewById(R.id.et_start_time);
        etCapacity = findViewById(R.id.et_capacity);
        spinnerClassTypes = findViewById(R.id.spinner_class_types);
        setListeners();

        classTypesNameList = new ArrayList<>();
        classTypesList = new ArrayList<>();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllClassTypes(databaseHelper.getReadableDatabase());
        while (cursor.moveToNext()) {
            int indexClassTypesNames = cursor.getColumnIndex(DatabaseHelper.TYPE_NAME);
            int indexClassTypesIds = cursor.getColumnIndex(DatabaseHelper.TYPE_ID);
            int indexClassDescription = cursor.getColumnIndex(DatabaseHelper.TYPE_DESCRIPTION);
            String name = cursor.getString(indexClassTypesNames);
            String description = cursor.getString(indexClassDescription);
            int id = cursor.getInt(indexClassTypesIds);
            classTypesList.add(
                    new ClassType(id, name, description)
            );
            classTypesNameList.add(name);
        }
        databaseHelper.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                classTypesNameList
        );
        spinnerClassTypes.setAdapter(adapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setListeners() {
        etEndTime.setOnClickListener(view -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ActivityAddClass.this, (timePicker, selectedHour, selectedMinute) -> etEndTime.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute)), hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        etStartTime.setOnClickListener(view -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ActivityAddClass.this, (timePicker, selectedHour, selectedMinute) -> etStartTime.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute)), hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        btnSaveClass.setOnClickListener(view -> {
            boolean etStartTimeFilled = isFieldFilled(etStartTime);
            boolean etEndTimeFilled = isFieldFilled(etEndTime);
            boolean etCapacityFilled = isFieldFilled(etCapacity);
           spinnerGetDay = findViewById(R.id.spinner_days);
           spinnerGetDifficulty = findViewById(R.id.spinner_difficulty);

           /*
                TO CHECK WHETHER ALL THE FIELDS ARE FILLED OR NOT (IN NOT CASE IT WILL ALSO SET ERROR MESSAGE)
            */
            if (etStartTimeFilled && etEndTimeFilled && etCapacityFilled) {

                /*
                       EXTRA CHECK ON START TIME AND END TIME BECAUSE THE END TIME MUST BE AFTER END TIME
                 */
                if (MyUtils.validateFieldTIme(etStartTime.getText().toString(), etEndTime.getText().toString())) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(this);
                    Cursor cursor = databaseHelper.getAllClassesTIme(databaseHelper.getReadableDatabase(), spinnerGetDay.getSelectedItemPosition());


                    /*
                            CHECKING ALL THE TIME RECORDS FOR DATABASE WHETHER THERE IS CLASH BETWEEN CLASSES
                     */
                    boolean isInBetween = false;
                    while (cursor.moveToNext()) {
                        int indexClassStartTime = cursor.getColumnIndex(DatabaseHelper.CLASS_START_TIME);
                        int indexClassEndTime = cursor.getColumnIndex(DatabaseHelper.CLASS_END_TIME);
                        int indexClassDay = cursor.getColumnIndex(DatabaseHelper.CLASS_DAY_ID);
                        String startTime = cursor.getString(indexClassStartTime);
                        String endTime = cursor.getString(indexClassEndTime);
                        int dayId = cursor.getInt(indexClassDay);
                        if (checkBetweenTime(startTime, endTime, etStartTime.getText().toString(), dayId)) {
                            isInBetween = true;
                            break;
                        }
                    }
                    if (!isInBetween) {
                        ContentValues values = new ContentValues();
                        values.put(DatabaseHelper.CLASS_CAPACITY, etCapacity.getText().toString());
                        values.put(DatabaseHelper.CLASS_TYPE_ID, classTypesList.get(spinnerClassTypes.getSelectedItemPosition()).getClassTypeID());
                        values.put(DatabaseHelper.CLASS_START_TIME, etStartTime.getText().toString());
                        values.put(DatabaseHelper.CLASS_END_TIME, etEndTime.getText().toString());
                        values.put(DatabaseHelper.CLASS_DAY_ID, spinnerGetDay.getSelectedItemPosition());
                        values.put(DatabaseHelper.CLASS_DIFFICULTY, spinnerGetDifficulty.getSelectedItem().toString());
                        values.put(DatabaseHelper.CLASS_INSTRUCTOR_ID, USER_ID);
                        values.put(DatabaseHelper.CLASS_INSTRUCTOR_NAME, ActivityManageClasses.instructorName);
                        values.put(DatabaseHelper.CLASS_NAME, spinnerClassTypes.getSelectedItem().toString());
                        databaseHelper.getWritableDatabase().insert(DatabaseHelper.CLASSES_TABLE_NAME, null,values);
                        databaseHelper.close();
                        Toast.makeText(ActivityAddClass.this, "Class added successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, ActivityManageClasses.class));
                        finish();
                    }
                }
                else {
                    etStartTime.setError("Start time should before end time");
                    etEndTime.setError("End time should after before time");
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

    private boolean checkBetweenTime(String startTime, String endTime, String checkTime, int dayId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US);
        LocalTime startLocalTime = LocalTime.parse(startTime, formatter);
        LocalTime endLocalTime = LocalTime.parse(endTime, formatter);
        LocalTime checkLocalTime = LocalTime.parse(checkTime, formatter);

        boolean isInBetween = false;
        if (spinnerGetDay.getSelectedItemPosition() == dayId) {
            if (startTime.equals(checkTime)) {
                isInBetween = true;
            }
            else if (startLocalTime.isAfter(checkLocalTime) && endLocalTime.isBefore(checkLocalTime)) {
                isInBetween = true;
            }
        }

        return isInBetween;
    }




}