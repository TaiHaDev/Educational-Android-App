package com.example.ga_23s1_comp2100_6442.model;

import java.util.List;
import java.util.Set;

public abstract class User {
    String userName;
    String name;
    String institution;
    List<Request> requests;
    public User() {
    }

    public User(String userName, String name, String institution) {
        this.userName = userName;
        this.name = name;
        this.institution = institution;
    }

    public abstract void sendRequest(Request request);
    public abstract void acceptRequest(Request request);

    public abstract void denyRequest(Request request);

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
