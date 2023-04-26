package com.example.ga_23s1_comp2100_6442.model;

import java.io.Serializable;
import java.util.List;

/**
 * Course is a model class to model the courses data fetched from Firebase containing the important
 * attribute sto display on screen for end user
 *
 * @author taiha
 */
public class Course {
    private String courseId;
    private String title;
    private String author;
    private String link;
    private String thumbnail;
    private List<String> studentsEnrolled;
    private List<String> StudentsApplied;
    private List<String> Lecturers;
    private boolean isPublic;


    public Course(String title, String author, String link, String thumbnail, boolean isPublic) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.thumbnail = thumbnail;
        this.isPublic = isPublic;
    }

    public Course(String title, String author, String link, String thumbnail, boolean isPublic,List<String> studentsEnrolled) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.thumbnail = thumbnail;
        this.isPublic = isPublic;
        this.studentsEnrolled=studentsEnrolled;
    }

    public Course() {

    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLink() {
        return link;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public List<String> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public void setStudentsEnrolled(List<String> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public List<String> getStudentsApplied() {
        return StudentsApplied;
    }

    public void setStudentsApplied(List<String> studentsApplied) {
        StudentsApplied = studentsApplied;
    }

    public List<String> getLecturers() {
        return Lecturers;
    }

    public void setLecturers(List<String> lecturers) {
        Lecturers = lecturers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
