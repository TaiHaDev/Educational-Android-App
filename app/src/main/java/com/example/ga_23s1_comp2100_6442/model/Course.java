package com.example.ga_23s1_comp2100_6442.model;

import android.graphics.Bitmap;

import java.util.Arrays;
import java.util.List;

/**
 * Course is a model class to model the courses data fetched from Firebase containing the important
 * attribute sto display on screen for end user
 * @author taiha
 */
public class Course {
    private String author;
    private String title;
    private String thumbnail;
    private List<String> link;
    private String description;
    private List<String> filters;
    private List<String>  searchTerm;


    public Course(String author, String title, String thumbnail, List<String> link, String description, List<String> filters, List<String> searchTerm) {
        this.author = author;
        this.title = title;
        this.thumbnail = thumbnail;
        this.link = link;
        this.description = description;
        this.filters = filters;
        this.searchTerm = searchTerm;
    }

    public Course() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getLink() {
        return link;
    }

    public void setLink(List<String> link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public List<String> getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(List<String> searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public String toString() {
        return "Course{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", link=" + link +
                ", description='" + description + '\'' +
                ", filters=" + filters +
                ", searchTerm=" + searchTerm +
                '}';
    }
}
