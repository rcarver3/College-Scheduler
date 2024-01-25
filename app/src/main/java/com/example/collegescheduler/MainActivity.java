package com.example.collegescheduler;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegescheduler.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> mobileCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createGroupList();
        createCollection();
        expandableListView = findViewById(R.id.elvMobiles);
        expandableListAdapter = new MyExpandableListAdapter(this, groupList, mobileCollection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long id) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                Toast.makeText(getApplicationContext(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void createCollection() {
        String[] csInfo = {"Objects and Design", "12:30 - 1:45", "Pedro"};
        String[] eceInfo = {"Power, Arch, and Conc", "9:30 - 10:45", "Brothers"};
        String[] mathInfo = {"Diff Eq", "2:00 - 3:15", "Su"};
        mobileCollection = new HashMap<String, List<String>>();
        for (String group : groupList) {
            if (group.equals("CS 2340")) {
                loadChild(csInfo);
            } else if (group.equals("ECE 3058")) {
                loadChild(eceInfo);
            } else {
                loadChild(mathInfo);
            }
            mobileCollection.put(group, childList);
        }
    }

    private void loadChild(String[] classes) {
        childList = new ArrayList<>();
        for (String cl : classes) {
            childList.add(cl);
        }
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("CS 2340");
        groupList.add("ECE 3058");
        groupList.add("MATH 2552");
    }
}