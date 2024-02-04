package com.example.collegescheduler;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

public class ToDoListFr extends AppCompatActivity {
    private ArrayList<String> items;
    private ListView taskList;
    private Button addTaskBtn;
    private ArrayAdapter<String> itemsAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolistfr);

        taskList = findViewById(R.id.taskList);
        addTaskBtn = findViewById(R.id.addTaskBtn);

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additem(view);
            }
        });

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        taskList.setAdapter(itemsAdapter);
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return remove(position);
            }
        });
    }

    private boolean remove(int position) {
        Context context = getApplicationContext();
        Toast.makeText(context, "", Toast.LENGTH_LONG).show();
        items.remove(position);
        itemsAdapter.notifyDataSetChanged();
        return true;
    }
    private void additem(View view) {
        EditText input = findViewById(R.id.edit_text);
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))){
            itemsAdapter.add(itemText);
            input.setText("");
        }

        else {
            Toast.makeText(getApplicationContext(), "Please enter task..", Toast.LENGTH_SHORT).show();
        }
    }

}
