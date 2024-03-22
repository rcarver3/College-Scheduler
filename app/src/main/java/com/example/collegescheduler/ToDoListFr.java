package com.example.collegescheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;

import java.util.Comparator;

public class ToDoListFr extends AppCompatActivity implements RecyclerViewInterface{
    protected static ArrayList<ToDoListInterface> tasks = new ArrayList<>();

    Context context;

    ArrayList<ClassModel> classModels = MainActivity.classModels;

    Boolean isAllButtonsVisible;
    TextView emptyTasks;
    ImageButton addInfo;

    int position;

    TextView addExamText;
    ImageButton addAssignments;
    TextView addAssignmentsText;


    Button homeBtn;
    ImageButton filterBtn;
    private ListTaskAdapter taskAdapter;

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

        emptyTasks = findViewById(R.id.empty_todo);



        addAssignments.setVisibility(View.GONE);
        addExamText.setVisibility(View.GONE);
        addAssignmentsText.setVisibility(View.GONE);

        filterBtn=findViewById(R.id.filterBtn);

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMultiChoiceDialog();
            }
        });

        RecyclerView tasksRecyclerView = findViewById(R.id.assignmentsRecyclerView3);

        for (ClassModel classModel : classModels) {
            if (!(tasks.containsAll(classModel.getExams()))) {
                tasks.addAll(classModel.getExams());
            } else if (!(tasks.containsAll(classModel.getAssignments()))) {
                tasks.addAll(classModel.getAssignments());
            }
        }

        if (!tasks.isEmpty()) {
            emptyTasks.setText("");
        }

        taskAdapter = new ListTaskAdapter(context, tasks, this);
        tasksRecyclerView.setAdapter(taskAdapter);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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


        homeBtn = findViewById(R.id.toDoPageBtn2);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListFr.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showMultiChoiceDialog() {
        // Items to display in the dialog
        final String[] items = new String[]{"Exams", "Assignments"};
        // This array will hold the items' selection states
        final boolean[] checkedItems = new boolean[]{false, false};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Filter by");

        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the current focused item's checked status
                checkedItems[which] = isChecked;
                // You can also perform other actions based on item selection changes
            }
        });

        // Adding "OK" button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean includeExams = checkedItems[0];
                boolean includeAssignments = checkedItems[1];
                filterTasksByLocation(includeExams, includeAssignments);
                // Handle the "OK" click here
                // For example, you can save the selections or perform actions based on them
            }
        });

        // Adding "Cancel" button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "Cancel" click here
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void filterTasksByLocation(boolean includeExams, boolean includeAssignments) {
        ArrayList<ToDoListInterface> filteredList = new ArrayList<>();

        for (ClassModel classModel : classModels) {
            // Add exams if includeExams is true
            if (includeExams) {
                for (ExamModel exam : classModel.getExams()) {
                    //filteredList.add(exam);
                    if (!exam.getLoc().isEmpty()) { // Assuming exams have non-empty location
                        filteredList.add(exam);
                    }
                }
            }

            // Add assignments if includeAssignments is true
            if (includeAssignments) {
                for (AssignmentModel assignment : classModel.getAssignments()) {
                    // filteredList.add(assignment);
                    if (assignment.getLoc().isEmpty()) { // Assuming assignments have empty location
                        filteredList.add(assignment);
                    }
                }
            }
        }

        // Update tasks and refresh RecyclerView
        tasks.clear();
        tasks.addAll(filteredList);
        taskAdapter.notifyDataSetChanged();
        if (tasks.isEmpty()) {
            emptyTasks.setText("No tasks found");
        } else {
            emptyTasks.setText("");
        }
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
        emptyTasks.setText("");

        addAssignments.setVisibility(View.GONE);
        addAssignmentsText.setVisibility(View.GONE);
        addExamText.setVisibility(View.GONE);

        isAllButtonsVisible = false;
    }

    private void addExam() {
        ExamModel exam = new ExamModel();
        collectExamName(exam);
        MainActivity.adapter.notifyItemChanged(position);
        emptyTasks.setText("");

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
                tasks.add(0, model);
                taskAdapter.notifyItemInserted(0);
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
                tasks.add(0, model);
                taskAdapter.notifyItemInserted(0);
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
        builder.setMessage(String.format("Are you sure you want to delete %s?", tasks.get(position).getName()));
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (model.equals("TASK")) {
                            tasks.remove(position);
                            taskAdapter.notifyItemRemoved(position);
                            if (tasks.isEmpty()) {
                                emptyTasks.setText(R.string.empty_todo);
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
