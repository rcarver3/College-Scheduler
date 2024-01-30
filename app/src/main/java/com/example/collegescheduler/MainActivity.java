package com.example.collegescheduler;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.AbsListView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    ArrayList<ClassModel> classModels = new ArrayList<>();
    int[] classImages = {R.drawable.side_nav_bar, R.drawable.side_nav_bar,
                        R.drawable.side_nav_bar, R.drawable.side_nav_bar,
                        R.drawable.side_nav_bar};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.classRecyclerView);

        setUpClassModels();

        Class_RecyclerViewAdapter adapter = new Class_RecyclerViewAdapter(this,
                classModels);
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
}