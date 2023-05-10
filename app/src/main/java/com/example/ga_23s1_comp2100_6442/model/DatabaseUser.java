package com.example.ga_23s1_comp2100_6442.model;

public class DatabaseUser {
    private String uid;
    private String name;
    private String email;
    public DatabaseUser() {}
    public DatabaseUser(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public DatabaseUser(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
}
