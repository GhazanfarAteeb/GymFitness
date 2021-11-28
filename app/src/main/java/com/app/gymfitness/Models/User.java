package com.app.gymfitness.Models;

public class User {
    int id;
    String name;
    String email;
    int gender;

    public User(int id, String name, String email, int gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        if(gender ==1) {
            return "Male";
        }
        else {
            return "Female";
        }
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
