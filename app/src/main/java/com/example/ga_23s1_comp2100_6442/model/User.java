package com.example.ga_23s1_comp2100_6442.model;

import java.util.List;
import java.util.Set;

public abstract class User {
    String userName;
    String name;
    String institution;
    String id;
    List<String> requestsGet;
    List<String> requestsSent;
    Set<User> followers;
    Set<User> following;

    public User() {
    }

    public User(String userName, String name, String institution,String id) {
        this.userName = userName;
        this.name = name;
        this.institution = institution;
        this.id =id;
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

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

}
