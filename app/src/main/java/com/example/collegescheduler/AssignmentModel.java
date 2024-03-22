package com.example.collegescheduler;

import java.io.Serializable;

public class AssignmentModel implements Serializable, ToDoListInterface {
    String name;
    String dueDate;
    String dueTime;

    public AssignmentModel(String name, String dueDate, String dueTime) {
        this.name = name;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }

    public AssignmentModel() {
        this(null, null, null);
    }
    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public String getLoc() {return "";}

    public void setName(String name) {
        this.name = name;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
}