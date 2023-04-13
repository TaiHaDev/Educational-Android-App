package com.example.ga_23s1_comp2100_6442.model;

public abstract class User {
    String userName;
    String name;
    String institution;

    public User() {
    }

    public User(String userName, String name, String institution) {
        this.userName = userName;
        this.name = name;
        this.institution = institution;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getInstitution() {
        return institution;
    }
}
