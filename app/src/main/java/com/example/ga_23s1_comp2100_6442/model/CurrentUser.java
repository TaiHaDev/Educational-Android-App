package com.example.ga_23s1_comp2100_6442.model;

public class CurrentUser extends User{
    private static CurrentUser instance;
    private CurrentUser() {}
    public CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }
}
