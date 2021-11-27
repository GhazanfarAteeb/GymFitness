package com.app.gymfitness.Models;

public class ClassType {
    private int classTypeID;
    private String className;
    private String description;

    public ClassType(int classTypeID, String className, String description) {
        this.classTypeID = classTypeID;
        this.className = className;
        this.description = description;
    }

    public int getClassTypeID() {
        return classTypeID;
    }

    public void setClassTypeID(int classTypeID) {
        this.classTypeID = classTypeID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
