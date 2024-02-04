package com.example.collegescheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ClassDetailsActivity extends AppCompatActivity implements RecyclerViewInterface {
    Context context;
    ArrayList<ClassModel> classModels = new ArrayList<>();
    ArrayList<AssignmentModel> assignmentModels;
    ImageButton backBtn;
    ImageButton addInfo;
    TextView addExamText;
    ImageButton addAssignments;
    TextView addAssignmentsText;
    Boolean isAllButtonsVisible;
    Class_Assignments_RecyclerViewAdapter assignmentsAdapter;
    Class_Exams_RecyclerViewAdapter examsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        context = this;

        addInfo = findViewById(R.id.more_actions);
        addExamText = findViewById(R.id.add_exam_text);
        addAssignments = findViewById(R.id.add_assignments);
        addAssignmentsText = findViewById(R.id.add_assignment_text);

        isAllButtonsVisible = false;

        Toolbar toolbar = findViewById(R.id.appbar_main);
        setSupportActionBar(toolbar);
        setTitle("");

        backBtn = findViewById(R.id.backButton);

        addAssignments.setVisibility(View.GONE);
        addExamText.setVisibility(View.GONE);
        addAssignmentsText.setVisibility(View.GONE);

        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAllButtonsVisible) {
                    expandButtons();
                } else {
                    addExam();
                }
            }
        });

        addAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllButtonsVisible) {
                    addAssignment();
                }
            }
        });

        TextView textView0 = findViewById(R.id.class_name);
        TextView textView1 = findViewById(R.id.class_day);
        TextView textView2 = findViewById(R.id.class_time);

        String className = getIntent().getStringExtra("NAME");
        String classDay = getIntent().getStringExtra("DAY");
        String classTime = getIntent().getStringExtra("TIME");
        assignmentModels = (ArrayList<AssignmentModel>) getIntent().getSerializableExtra("ASSIGNMENTS");

        textView0.setText(className);
        textView1.setText(classDay);
        textView2.setText(classTime);

        RecyclerView assignmentsRecyclerView = findViewById(R.id.assignmentsRecyclerView);
        RecyclerView examsRecyclerView = findViewById(R.id.examsRecyclerView);

        assignmentsAdapter = new Class_Assignments_RecyclerViewAdapter(this,
                assignmentModels, this);
        assignmentsRecyclerView.setAdapter(assignmentsAdapter);
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        examsAdapter = new Class_Exams_RecyclerViewAdapter(this,
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

    private void expandButtons() {
        addAssignments.setVisibility(View.VISIBLE);
        addAssignmentsText.setVisibility(View.VISIBLE);
        addExamText.setVisibility(View.VISIBLE);

        isAllButtonsVisible = true;
    }
    private void addAssignment() {
        AssignmentModel assignment = new AssignmentModel();
        collectClassName(assignment);

        addAssignments.setVisibility(View.GONE);
        addAssignmentsText.setVisibility(View.GONE);
        addExamText.setVisibility(View.GONE);

        isAllButtonsVisible = false;
    }

    private void addExam() {
        addAssignments.setVisibility(View.GONE);
        addAssignmentsText.setVisibility(View.GONE);
        addExamText.setVisibility(View.GONE);

        isAllButtonsVisible = false;

    }

    private void collectClassName(AssignmentModel model) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Assignment");
        alert.setMessage("Assignment Name:");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                model.setName(input.getText().toString());
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

    private void collectClassDay(AssignmentModel model) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Assignment");
        alert.setMessage("Due Date:");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                model.setDueDate(input.getText().toString());
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

    private void collectClassTime(AssignmentModel model) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Assignment");
        alert.setMessage("Due Time:");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                model.setDueTime(input.getText().toString());
                assignmentModels.add(0, model);
                assignmentsAdapter.notifyItemInserted(0);
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

    }

    @Override
    public void onItemLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete?");
        builder.setMessage(String.format("Are you sure you want to delete %s?", assignmentModels.get(position).getName()));
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        classModels.remove(position);
                        assignmentsAdapter.notifyItemRemoved(position);
                        if (classModels.isEmpty()) {
                            // addClasses.setText(R.string.no_classes_yet);
                        }
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
}