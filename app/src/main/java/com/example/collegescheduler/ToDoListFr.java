package com.example.collegescheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

public class ToDoListFr extends AppCompatActivity implements RecyclerViewInterface{
    private ArrayList<Object> items;

    Context context;

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
    private ListView taskList;
    private Button addTaskBtn;

    Class_Assignments_RecyclerViewAdapter assignmentsAdapter;

    Class_Exams_RecyclerViewAdapter examsAdapter;

    Button homeBtn;
    private ListTaskAdapter itemsAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolistfr);

        position = getIntent().getIntExtra("POS", 0);
        addInfo = findViewById(R.id.more_actions2);
        addExamText = findViewById(R.id.add_exam_text3);
        addAssignments = findViewById(R.id.add_assignments3);
        addAssignmentsText = findViewById(R.id.add_assignment_text3);

        isAllButtonsVisible = false;

        emptyAssignments = findViewById(R.id.empty_todo);

        taskList = findViewById(R.id.assignmentsRecyclerView);

        /*
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

         */

        classes = MainActivity.getClassModels();
        items = new ArrayList<>();
        for (int i = 0; i < classes.size(); i++) {
            ClassModel classModel = classes.get(i);
            items.addAll(classModel.getAssignments());
            items.addAll(classModel.getExams());
        }

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

        homeBtn = findViewById(R.id.toDoPageBtn2);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListFr.this, MainActivity.class);
                startActivity(intent);
            }
        });

         */
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
