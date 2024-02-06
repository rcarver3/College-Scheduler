package com.example.collegescheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassModel {
    String className;
    String classDay;
    String classTime;
    String instructor;
    ArrayList<AssignmentModel> assignments;
    ArrayList<ExamModel> exams;
    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassDay(String classDay) {
        this.classDay = classDay;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }
    public void setAssignments(ArrayList<AssignmentModel> assignments) {this.assignments = assignments; }
    public void setExams(ArrayList<ExamModel> exams) {this.exams = exams;}

    public ClassModel(String className, String classDay,
                      String classTime, String instructor) {
        this.className = className;
        this.classDay = classDay;
        this.classTime = classTime;
        this.instructor = instructor;
        this.assignments = new ArrayList<>();
        this.exams = new ArrayList<>();
    }

    public ClassModel() {
        this(null, null, null, null);
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


    public ArrayList<AssignmentModel> getAssignments() {
        return assignments;
    }

    public ArrayList<ExamModel> getExams() {
        return exams;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
