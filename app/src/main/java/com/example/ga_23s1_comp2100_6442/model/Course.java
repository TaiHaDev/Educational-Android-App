package com.example.ga_23s1_comp2100_6442.model;

import android.graphics.Bitmap;

/**
 * Course is a model class to model the courses data fetched from Firebase containing the important
 * attribute sto display on screen for end user
 * @author taiha
 */
public class Course {
    private String name;
    private String author;
    private String link;
    private String thumbnail;
    public Course(String name, String author, String link, String thumbnail) {
        this.name = name;
        this.author = author;
        this.link = link;
        this.thumbnail = thumbnail;
    }
    public Course() {

    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getLink() {
        return link;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
