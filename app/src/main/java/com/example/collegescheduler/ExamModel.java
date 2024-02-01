package com.example.collegescheduler;

public class ExamModel {
    String name;
    String date;

    public ExamModel(String name, String date) {
        this.name = name;
        this.date = date;
    }
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}