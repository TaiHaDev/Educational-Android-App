package com.example.ga_23s1_comp2100_6442.model;

import java.io.Serializable;
import java.util.Set;

/**
 * Course is a model class to model the courses data fetched from Firebase containing the important
 * attribute sto display on screen for end user
 *
 * @author taiha
 */
public class Course implements Serializable {
    private String id;
    private String title;
    private String author;
    private String link;
    private String thumbnail;
    private Set<Student> studentsEnrolled;
    private Set<Student> StudentsApplied;
    private Set<Lecturer> Lecturers;
    private boolean isPublic;



    public Course(String title, String author, String link, String thumbnail, boolean isPublic) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.thumbnail = thumbnail;
        this.isPublic=isPublic;
    }

    public Course() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLink() {
        if (isPublic) {
            return link;
        } else {
            return "gs://comp2100-comp6442-assignment.appspot.com/dogs.mp4";
        }
    }
    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getThumbnail() {
        return thumbnail;
    }
    public boolean getIsPublic(){
        return isPublic;
    }

    public Set<Student> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public void setStudentsEnrolled(Set<Student> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public Set<Student> getStudentsApplied() {
        return StudentsApplied;
    }

    public void setStudentsApplied(Set<Student> studentsApplied) {
        StudentsApplied = studentsApplied;
    }

    public Set<Lecturer> getLecturers() {
        return Lecturers;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
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

    public boolean isPublic() {
        return isPublic;
    }
}
