package com.example.ga_23s1_comp2100_6442.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class User {
    boolean isLecturer;
    String userName;
    String name;
    String institution;
    String id;
    List<String> requestsGet;
    List<String> requestsSent;
    List<String> followers;
    List<String> following;

    public User() {
    }

    public User(String userName, String name, String institution, String id) {
        this.userName = userName;
        this.name = name;
        this.institution = institution;
        this.id = id;
        List<String> requestsGet = new ArrayList<>();
        List<String> requestsSent = new ArrayList<>();
        List<String> followers = new ArrayList<>();
        List<String> following = new ArrayList<>();
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRequestsGet() {
        return requestsGet;
    }

    public void setRequestsGet(List<String> requestsGet) {
        this.requestsGet = requestsGet;
    }

    public List<String> getRequestsSent() {
        return requestsSent;
    }

    public void setRequestsSent(List<String> requestsSent) {
        this.requestsSent = requestsSent;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
    }

    public boolean getIsLecturer() {
        return isLecturer;
    }

    public void setLecturer(boolean lecturer) {
        isLecturer = lecturer;
    }
}

