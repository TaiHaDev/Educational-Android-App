package com.example.ga_23s1_comp2100_6442.model;

import java.util.Map;
import java.util.Set;

public class Student extends User {
    private Map<String, Course> coursesEnrolled;

    public Student() {
    }

    public Student(String userName, String name, String institution,String id) {
        super(userName, name, institution,id);
    }



    public Map<String, Course> getCoursesEnrolled() {
        return coursesEnrolled;
    }

    public void setCoursesEnrolled(Map<String, Course> coursesEnrolled) {
        this.coursesEnrolled = coursesEnrolled;
    }
}
