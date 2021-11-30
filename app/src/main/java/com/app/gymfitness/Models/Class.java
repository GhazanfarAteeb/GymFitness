package com.app.gymfitness.Models;

import java.io.Serializable;

public class Class implements Serializable {
    int id;
    int capacity;
    int dayId;
    int instructorId;
    String startTime;
    String endTIme;
    String difficulty;
    String instructorName;
    String className;
    String description;

    public Class(int id, int instructorId, int capacity, int dayId, String startTime, String endTIme, String difficulty, String instructorName, String className, String description) {
        this.id = id;
        this.instructorId = instructorId;
        this.capacity = capacity;
        this.dayId = dayId;
        this.startTime = startTime;
        this.endTIme = endTIme;
        this.difficulty = difficulty;
        this.instructorName = instructorName;
        this.className = className;
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDifficulty() {
        return difficulty;
    }


    public int getDayId() {
        return dayId;
    }


    public String getStartTime() {
        return startTime;
    }


    public String getEndTIme() {
        return endTIme;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
}
