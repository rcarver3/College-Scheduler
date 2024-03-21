package com.example.collegescheduler;

public interface ToDoListInterface {

    public default String getDueDate() {
        return null;
    };
    public default String getName() {
        return null;
    };

    public default String getDueTime() {
        return null;
    }
    public default String getLocation(){
        return null;
    }
}

