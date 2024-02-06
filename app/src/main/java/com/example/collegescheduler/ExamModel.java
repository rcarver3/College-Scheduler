package com.example.collegescheduler;

public class ExamModel {
    String name;
    String date;
    String loc;

    public ExamModel(String name, String date, String loc) {
        this.name = name;
        this.date = date;
        this.loc = loc;
    }

    public ExamModel() {
        this(null, null, null);
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

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}