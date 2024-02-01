package com.example.collegescheduler;

public class AssignmentModel {
    String name;
    String dueDate;
    String dueTime;

    public AssignmentModel(String name, String dueDate, String dueTime) {
        this.name = name;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
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
}