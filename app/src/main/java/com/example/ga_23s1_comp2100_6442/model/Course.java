package com.example.ga_23s1_comp2100_6442.model;

import android.graphics.Bitmap;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Course is a model class to model the courses data fetched from Firebase containing the important
 * attribute sto display on screen for end user
 * @author taiha
 */
public class Course implements Comparable<Course>{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(author, course.author) && Objects.equals(title, course.title) && Objects.equals(thumbnail, course.thumbnail) && Objects.equals(link, course.link) && Objects.equals(description, course.description) && Objects.equals(filters, course.filters) && Objects.equals(searchTerm, course.searchTerm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, thumbnail, link, description, filters, searchTerm);
    }


    @Override
    public int compareTo(Course course) {
        if (this.hashCode() > course.hashCode()) return 1;
        else if (this.hashCode() < course.hashCode()) return -1;
        return 0;
    }
}
