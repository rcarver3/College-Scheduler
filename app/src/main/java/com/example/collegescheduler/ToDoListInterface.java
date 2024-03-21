package com.example.collegescheduler;

public interface ToDoListInterface {

    public default String getDueDate() {
        return null;
    };
}
