package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.gymfitness.Adapters.MembersAdapter;
import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.Models.User;
import com.app.gymfitness.R;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RadioButton instructor, member;
    List<User> usersList;
    MembersAdapter adapter;
    RadioGroup group;
    MembersAdapter.PassData passData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        recyclerView = findViewById(R.id.recycler_view);
        instructor = findViewById(R.id.rb_instructors);
        member = findViewById(R.id.rb_members);
        group = findViewById(R.id.rg_users);
        usersList = new ArrayList<>();

        instructor.setChecked(true);
        setData(1);
        instructor.setOnClickListener(view -> setData(1));
        member.setOnClickListener(view -> setData(2));

        passData = user -> {
            DatabaseHelper databaseHelper = new DatabaseHelper(ManageUsersActivity.this);
            if (member.isChecked()) {
                databaseHelper.getReadableDatabase().execSQL(
                        "DELETE FROM " + DatabaseHelper.USER_TABLE_NAME + " WHERE " + DatabaseHelper.USER_ID + "=" + user.getId()
                );
                setData(1);
            }
            else if (instructor.isChecked()) {
                // DELETING THE EXISTING USER
                databaseHelper.getReadableDatabase().execSQL(
                        "DELETE FROM " + DatabaseHelper.USER_TABLE_NAME + " WHERE " + DatabaseHelper.USER_ID + "=" + user.getId()
                );
                // FETCHING ALL THE CLASSES THE USER TO BE DELETED
                Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(
                        "SELECT * FROM " + DatabaseHelper.CLASSES_TABLE_NAME + " WHERE " + DatabaseHelper.CLASS_INSTRUCTOR_ID + "=" + user.getId(),
                        null
                );
                // DELETING ALL THE CLASSES ENROLLMENT RECORD OF THE INSTRUCTOR
                while (cursor.moveToNext()) {
                    int classIdIndex = cursor.getColumnIndex(DatabaseHelper.ENROLLMENT_CLASS_ID);
                    databaseHelper.getReadableDatabase().execSQL(
                            "DELETE FROM " + DatabaseHelper.ENROLLMENT_TABLE_NAME + " WHERE " + DatabaseHelper.CLASS_ID + "=" + cursor.getInt(classIdIndex)
                    );
                }
                cursor.close();
                // DELETING ALL THE CLASSES OF THE INSTRUCTOR
                databaseHelper.getReadableDatabase().execSQL(
                        "DELETE FROM " + DatabaseHelper.CLASSES_TABLE_NAME + " WHERE " + DatabaseHelper.CLASS_INSTRUCTOR_ID + "=" + user.getId()
                );
                setData(2);
            }
        };

    }

    public void setData(int typeId) {
        adapter = new MembersAdapter(this);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllUserByType(databaseHelper.getWritableDatabase(), typeId);
        usersList.clear();
        usersList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idColumnIndex = cursor.getColumnIndex(DatabaseHelper.USER_ID);
            int nameColumnIndex = cursor.getColumnIndex(DatabaseHelper.USER_NAME);
            int emailColumnIndex = cursor.getColumnIndex(DatabaseHelper.USER_EMAIL);
            int genderColumnIndex = cursor.getColumnIndex(DatabaseHelper.USER_GENDER);
            usersList.add(
                    new User(
                            cursor.getInt(idColumnIndex),
                            cursor.getString(nameColumnIndex),
                            cursor.getString(emailColumnIndex),
                            cursor.getInt(genderColumnIndex)
                    )
            );
        }
        cursor.close();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(usersList);
        adapter.setPassDataListener(passData);
    }
}