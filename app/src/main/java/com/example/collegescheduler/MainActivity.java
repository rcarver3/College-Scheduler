package com.example.collegescheduler;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<ClassModel> classModels = new ArrayList<>();
    int[] classImages = {R.drawable.side_nav_bar, R.drawable.side_nav_bar,
                        R.drawable.side_nav_bar, R.drawable.side_nav_bar,
                        R.drawable.side_nav_bar};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_classes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.classRecyclerView);

        setUpClassModels();

        Class_RecyclerViewAdapter adapter = new Class_RecyclerViewAdapter(this,
                classModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        showClassDetails(position);
    }

    private void showClassDetails(int position) {
        Intent intent = new Intent(this, ClassDetailsActivity.class);

        intent.putExtra("NAME", classModels.get(position).getClassName());
        intent.putExtra("DAY", classModels.get(position).getClassDay());
        intent.putExtra("TIME", classModels.get(position).getClassTime());

        startActivity(intent);
    }
}