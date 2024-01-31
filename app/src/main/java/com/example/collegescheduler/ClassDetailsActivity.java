package com.example.collegescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ClassDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        TextView textView0 = findViewById(R.id.class_name);
        TextView textView1 = findViewById(R.id.class_day);
        TextView textView2 = findViewById(R.id.class_time);

        String className = getIntent().getStringExtra("NAME");
        String classDay = getIntent().getStringExtra("DAY");
        String classTime = getIntent().getStringExtra("TIME");

        textView0.setText(className);
        textView1.setText(classDay);
        textView2.setText(classTime);
    }
}