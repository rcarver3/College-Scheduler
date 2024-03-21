package com.example.collegescheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Collections;
import java.util.Comparator;

public class ToDoListFr extends AppCompatActivity implements RecyclerViewInterface{
    private ArrayList<Object> items;

    Context context;

    ArrayList<ClassModel> classModels = MainActivity.classModels;

    Boolean isAllButtonsVisible;

    ArrayList<AssignmentModel> assignmentModels;
    ArrayList<ExamModel> examModels;
    private ArrayList<ClassModel> classes;

    TextView emptyAssignments;
    TextView emptyExams;
    ImageButton addInfo;

    int position;

    TextView addExamText;
    ImageButton addAssignments;
    TextView addAssignmentsText;
    private  ArrayList<Object> taskList;
    private Button addTaskBtn;

    Class_Assignments_RecyclerViewAdapter assignmentsAdapter;

    Class_Exams_RecyclerViewAdapter examsAdapter;

    Button homeBtn;
    private ListTaskAdapter itemsAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolistfr);
        context = this;


        position = getIntent().getIntExtra("POS", 0);
        addInfo = findViewById(R.id.more_actions2);
        addExamText = findViewById(R.id.add_exam_text3);
        addAssignments = findViewById(R.id.add_assignments3);
        addAssignmentsText = findViewById(R.id.add_assignment_text3);


        isAllButtonsVisible = false;

        emptyAssignments = findViewById(R.id.empty_todo);

        //taskList = findViewById(R.id.assignmentsRecyclerView3);

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




        /*
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

         */

        /*
        itemsAdapter = new ListTaskAdapter(this, items);
        taskList.setAdapter(itemsAdapter);
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return remove(position);
            }
        });

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editItem(position);
            }
        });
         */

        homeBtn = findViewById(R.id.toDoPageBtn2);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListFr.this, MainActivity.class);
                startActivity(intent);
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

    private ArrayList<ToDoListInterface> getSortedTodoItems() {
        ArrayList<ToDoListInterface> items = new ArrayList<>();
        // Assuming classModels is accessible and contains your data
        for (ClassModel classModel : classModels) {
            items.addAll(classModel.getAssignments()); // Ensure these lists actually hold TodoItemInterface objects
            items.addAll(classModel.getExams());
        }
        // Sort items by due date; implement actual date comparison for real applications
        items.sort(Comparator.comparing(ToDoListInterface::getDueDate));
        return items;
    }


    private boolean remove(int position) {
        Context context = getApplicationContext();
        Toast.makeText(context, "", Toast.LENGTH_LONG).show();
        items.remove(position);
        itemsAdapter.notifyDataSetChanged();
        return true;
    }

    /*
    private void addItem(View view) {
        EditText input = findViewById(R.id.edit_text);
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))){
            ;
            input.setText("");
        }

        else {
            Toast.makeText(getApplicationContext(), "Please enter task..", Toast.LENGTH_SHORT).show();
        }
    }
     */

    /*
    private void editItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Item");

        final EditText input = new EditText(this);
        input.setText(items.get(position).toString());
        builder.setView(input);


        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String itemText = input.getText().toString();
                if (!itemText.equals("")) {
                    items.set(position, itemText);
                    itemsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Task cannot be empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

     */

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
                                emptyAssignments.setText(R.string.no_classes_yet);
                            }
                        } else if (model.equals("EXAM")) {
                            examModels.remove(position);
                            examsAdapter.notifyItemRemoved(position);
                            if (examModels.isEmpty()) {
                                emptyExams.setText(R.string.no_classes_yet);
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
