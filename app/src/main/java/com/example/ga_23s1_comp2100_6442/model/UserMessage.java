package com.example.ga_23s1_comp2100_6442.model;

public class UserMessage {
    private String text;
    private String name;
    private String imageURL;
    public UserMessage(){}

    public UserMessage(String text, String name, String imageURL) {
        this.text = text;
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


}
