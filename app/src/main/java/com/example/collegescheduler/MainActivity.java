package com.example.collegescheduler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<ClassModel> classModels = new ArrayList<>();
    String[] classNames;
    String[] classDays;
    String[] classTimes;
    Class_RecyclerViewAdapter adapter;
    FloatingActionButton addBtn;


    int[] classImages = {R.drawable.side_nav_bar, R.drawable.side_nav_bar,
                        R.drawable.side_nav_bar, R.drawable.side_nav_bar,
                        R.drawable.side_nav_bar};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");



        addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClassModel();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.classRecyclerView);

        adapter = new Class_RecyclerViewAdapter(this,
                classModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addClassModel() {
        ClassModel model = new ClassModel();
        collectClassName(model);
    }

    private void collectClassName(ClassModel model) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Class");
        alert.setMessage("Class Name:");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                model.setClassName(input.getText().toString());
                collectClassDay(model);
                // Do something with value!
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    private void collectClassDay(ClassModel model) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Class");
        alert.setMessage("Class Days:");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                model.setClassDay(input.getText().toString());
                collectClassTime(model);
                // Do something with value!
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    private void collectClassTime(ClassModel model) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Class");
        alert.setMessage("Class Time:");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                model.setClassTime(input.getText().toString());
                model.setImage(R.drawable.side_nav_bar);
                classModels.add(0, model);
                adapter.notifyItemInserted(0);
                // Do something with value!
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }


    @Override
    public void onItemClick(int position) {
        showClassDetails(position);
    }

    @Override
    public void onItemLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete?");
        builder.setMessage(String.format("Are you sure you want to delete %s?", classModels.get(position).getClassName()));
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        classModels.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showClassDetails(int position) {
        Intent intent = new Intent(this, ClassDetailsActivity.class);

        intent.putExtra("NAME", classModels.get(position).getClassName());
        intent.putExtra("DAY", classModels.get(position).getClassDay());
        intent.putExtra("TIME", classModels.get(position).getClassTime());

        startActivity(intent);
    }
}