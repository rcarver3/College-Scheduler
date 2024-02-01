package com.example.collegescheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassModel {
    String className;
    String classDay;
    String classTime;
    List<AssignmentModel> assignments;
    List<ExamModel> exams;
    int image;

    public void setImage(int image) {
        this.image = image;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassDay(String classDay) {
        this.classDay = classDay;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public ClassModel(String className, String classDay,
                      String classTime, int image) {
        this.className = className;
        this.classDay = classDay;
        this.classTime = classTime;
        this.image = image;
        this.assignments = new ArrayList<>();
        this.exams = new ArrayList<>();
    }

    public ClassModel() {
        this(null, null, null, R.drawable.side_nav_bar);
    }

    public String getClassName() {
        return className;
    }

    public String getClassDay() {
        return classDay;
    }

    public String getClassTime() {
        return classTime;
    }

    public int getImage() {
        return image;
    }

    public List<AssignmentModel> getAssignments() {
        return assignments;
    }

    public List<ExamModel> getExams() {
        return exams;
    }
}
