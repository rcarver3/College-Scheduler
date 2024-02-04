package com.example.collegescheduler;

public class ExamModel {
    String name;
    String date;

    public ExamModel(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public ExamModel() {
        this(null, null);
    }
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }
}