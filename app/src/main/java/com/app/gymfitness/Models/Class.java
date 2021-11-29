package com.app.gymfitness.Models;

import java.io.Serializable;

public class Class implements Serializable {
    int id;
    int name;
    int description;
    int capacity;
    String difficulty;
    int classTypeId;
    int dayId;
    String startTime;
    String endTIme;

    public Class(int id, int name, int description, int capacity, String difficulty, int classTypeId, int dayId, String startTime, String endTIme) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.difficulty = difficulty;
        this.classTypeId = classTypeId;
        this.dayId = dayId;
        this.startTime = startTime;
        this.endTIme = endTIme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getClassTypeId() {
        return classTypeId;
    }

    public void setClassTypeId(int classTypeId) {
        this.classTypeId = classTypeId;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTIme() {
        return endTIme;
    }

    public void setEndTIme(String endTIme) {
        this.endTIme = endTIme;
    }
}
