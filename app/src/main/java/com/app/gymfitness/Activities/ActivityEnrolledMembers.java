package com.app.gymfitness.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.app.gymfitness.Adapters.EnrolledMembersAdapter;
import com.app.gymfitness.DatabaseHelper.DatabaseHelper;
import com.app.gymfitness.Models.Class;
import com.app.gymfitness.Models.User;
import com.app.gymfitness.R;

import java.util.List;

public class ActivityEnrolledMembers extends AppCompatActivity {
    RecyclerView recyclerView;
    List<User> enrolledMembersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled_members);
        Class myClass = (Class) getIntent().getSerializableExtra("class");
        ((TextView) findViewById(R.id.tv_class_name)).setText(myClass.getClassName());
        ((TextView) findViewById(R.id.tv_instructor_name)).setText(myClass.getInstructorName());
        recyclerView = findViewById(R.id.recycler_view);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        enrolledMembersList = databaseHelper.getEnrolledMembersData(databaseHelper.getReadableDatabase(), myClass.getId());
        EnrolledMembersAdapter adapter = new EnrolledMembersAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(enrolledMembersList);
    }
}