package com.example.collegescheduler;

public interface ToDoListInterface {

    default void setName(String name) {}

    default String getDueDate() {
        return null;
    }
    default String getName() {
        return null;
    }

    default String getDueTime() {
        return null;
    }
    default String getLoc(){
        return null;
    }
}

