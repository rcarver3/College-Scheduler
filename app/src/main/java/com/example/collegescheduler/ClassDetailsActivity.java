package com.example.collegescheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ClassDetailsActivity extends AppCompatActivity implements RecyclerViewInterface {
    ArrayList<ClassModel> classModels = new ArrayList<>();
    ImageButton backBtn;
    int[] classImages = {R.drawable.side_nav_bar, R.drawable.side_nav_bar,
            R.drawable.side_nav_bar, R.drawable.side_nav_bar,
            R.drawable.side_nav_bar};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        Toolbar toolbar = findViewById(R.id.appbar_main);
        setSupportActionBar(toolbar);
        setTitle("");

        backBtn = findViewById(R.id.backButton);

        TextView textView0 = findViewById(R.id.class_name);
        TextView textView1 = findViewById(R.id.class_day);
        TextView textView2 = findViewById(R.id.class_time);

        String className = getIntent().getStringExtra("NAME");
        String classDay = getIntent().getStringExtra("DAY");
        String classTime = getIntent().getStringExtra("TIME");

        textView0.setText(className);
        textView1.setText(classDay);
        textView2.setText(classTime);

        RecyclerView assignmentsRecyclerView = findViewById(R.id.assignmentsRecyclerView);
        RecyclerView examsRecyclerView = findViewById(R.id.examsRecyclerView);

        setUpClassModels();

        Class_Assignments_RecyclerViewAdapter assignmentsAdapter = new Class_Assignments_RecyclerViewAdapter(this,
                classModels, this);
        assignmentsRecyclerView.setAdapter(assignmentsAdapter);
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Class_Exams_RecyclerViewAdapter examsAdapter = new Class_Exams_RecyclerViewAdapter(this,
                classModels, this);
        examsRecyclerView.setAdapter(examsAdapter);
        examsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setUpClassModels() {
        String[] classNames = getResources().getStringArray(R.array.test_names);
        String[] classDays = getResources().getStringArray(R.array.test_days);
        String[] classTimes = getResources().getStringArray(R.array.test_times);

        for (int i = 0; i < classNames.length; i++) {
            classModels.add(new ClassModel(classNames[i], classDays[i], classTimes[i], classImages[i]));
        }
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }
}