package com.example.ga_23s1_comp2100_6442.model;

import com.google.firebase.database.ServerValue;

public class Post {

    private String title;
    private String description;
    private String userName;

    private String userId;
    private Object timeStamp ;
    private String postId;


    public Post(String title, String description, String userName,String userId) {
        this.title = title;
        this.description = description;
        this.userName = userName;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    // make sure to have an empty constructor inside ur model class
    public Post() {
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }


    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
