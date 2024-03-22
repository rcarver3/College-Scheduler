
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
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;


public class ClassDetailsActivity extends AppCompatActivity implements RecyclerViewInterface {
    Context context;
    ArrayList<ClassModel> classModels = MainActivity.classModels;
    ArrayList<AssignmentModel> assignmentModels;
    ArrayList<ExamModel> examModels;
    TextView instructor;
    ImageButton backBtn;
    ImageButton addInfo;
    TextView addExamText;
    ImageButton addAssignments;
    TextView addAssignmentsText;
    Boolean isAllButtonsVisible;
    TextView emptyAssignments;
    TextView emptyExams;

    ArrayList<ToDoListInterface> tasks;

    Class_Assignments_RecyclerViewAdapter assignmentsAdapter;
    Class_Exams_RecyclerViewAdapter examsAdapter;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        context = this;

        position = getIntent().getIntExtra("POS", 0);
        addInfo = findViewById(R.id.more_actions);
        addExamText = findViewById(R.id.add_exam_text);
        addAssignments = findViewById(R.id.add_assignments);
        addAssignmentsText = findViewById(R.id.add_assignment_text);

        isAllButtonsVisible = false;

        emptyAssignments = findViewById(R.id.empty_assignments);
        emptyExams = findViewById(R.id.empty_exams);

        instructor = findViewById(R.id.instructor);
        Toolbar toolbar = findViewById(R.id.appbar_main);
        setSupportActionBar(toolbar);
        setTitle("");

        backBtn = findViewById(R.id.backButton);

        addAssignments.setVisibility(View.GONE);
        addExamText.setVisibility(View.GONE);
        addAssignmentsText.setVisibility(View.GONE);

        tasks = ToDoListFr.tasks;

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
        assignmentModels = MainActivity.classModels.get(position).getAssignments();
        examModels = MainActivity.classModels.get(position).getExams();

        assignmentModels.removeIf(assignmentModel -> !tasks.contains(assignmentModel));
        examModels.removeIf(examModel -> !tasks.contains(examModel));

        if (!assignmentModels.isEmpty()) {
            emptyAssignments.setText("");
        }
        if (!examModels.isEmpty()) {
            emptyExams.setText("");
        }
        textView0.setText(className);
        textView1.setText(classDay);
        textView2.setText(classTime);
        instructor.setText(getIntent().getStringExtra("INSTRUCTOR"));

        RecyclerView assignmentsRecyclerView = findViewById(R.id.assignmentsRecyclerView);
        RecyclerView examsRecyclerView = findViewById(R.id.examsRecyclerView);

        assignmentsAdapter = new Class_Assignments_RecyclerViewAdapter(this,
                assignmentModels, this);
        assignmentsRecyclerView.setAdapter(assignmentsAdapter);
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        examsAdapter = new Class_Exams_RecyclerViewAdapter(this,
                examModels, this);
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
        collectAssignmentName(assignment);
        MainActivity.adapter.notifyItemChanged(position);
        emptyAssignments.setText("");

        addAssignments.setVisibility(View.GONE);
        addAssignmentsText.setVisibility(View.GONE);
        addExamText.setVisibility(View.GONE);

        isAllButtonsVisible = false;
    }

    private void addExam() {
        ExamModel exam = new ExamModel();
        collectExamName(exam);
        MainActivity.adapter.notifyItemChanged(position);
        emptyExams.setText("");

        addAssignments.setVisibility(View.GONE);
        addAssignmentsText.setVisibility(View.GONE);
        addExamText.setVisibility(View.GONE);

        isAllButtonsVisible = false;

    }

    private void collectAssignmentName(AssignmentModel model) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Assignment");
        alert.setMessage("Assignment Name:");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                model.setName(input.getText().toString());
                collectDueDate(model);
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

    private void collectDueDate(AssignmentModel model) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Format the date selected by the user
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        model.setDueDate(selectedDate);
                        collectDueTime(model); // Proceed to collect due time
                    }
                }, year, month, day);

        datePickerDialog.setTitle("Select Due Date");
        datePickerDialog.show();
    }

    private void collectDueTime(AssignmentModel model) {
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
                classModels.get(position).setAssignments(assignmentModels);
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

    private void collectExamName(ExamModel model) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Exam");
        alert.setMessage("Exam Name:");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                model.setName(input.getText().toString());
                collectExamDate(model);
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

    private void collectExamDate(ExamModel model) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Format the date selected by the user
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        model.setDate(selectedDate);
                        collectExamLoc(model);
                        // Here, you can proceed to the next step, if there is any, or update your model and UI
                    }
                }, year, month, day);

        datePickerDialog.setTitle("Select Exam Date");
        datePickerDialog.show();
    }

    private void collectExamLoc(ExamModel model) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Exam");
        alert.setMessage("Exam Location:");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                model.setLoc(input.getText().toString());
                examModels.add(0, model);
                examsAdapter.notifyItemInserted(0);
                classModels.get(position).setExams(examModels);
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
    public void onItemLongClick(String model, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete?");
        if (model.equals("ASSIGNMENT")) {
            builder.setMessage(String.format("Are you sure you want to delete %s?", assignmentModels.get(position).getName()));
        } else if (model.equals("EXAM")) {
            builder.setMessage(String.format("Are you sure you want to delete %s?", examModels.get(position).getName()));
        }
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (model.equals("ASSIGNMENT")) {
                            assignmentModels.remove(position);
                            assignmentsAdapter.notifyItemRemoved(position);
                            if (assignmentModels.isEmpty()) {
                                emptyAssignments.setText(R.string.empty_todo);
                            }
                        } else if (model.equals("EXAM")) {
                            examModels.remove(position);
                            examsAdapter.notifyItemRemoved(position);
                            if (examModels.isEmpty()) {
                                emptyExams.setText(R.string.empty_todo);
                            }
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