package com.example.collegescheduler;

public class ClassModel {
    String className;
    String classDay;
    String classTime;
    int image;

    public ClassModel(String className, String classDay,
                      String classTime, int image) {
        this.className = className;
        this.classDay = classDay;
        this.classTime = classTime;
        this.image = image;
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
}
