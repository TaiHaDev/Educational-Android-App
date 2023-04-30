package com.example.ga_23s1_comp2100_6442.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Student extends User {
    private List<String> coursesEnrolled;
    private List<String> waitingList;

    public Student() {
    }
    

    public Student(String userName, String name, String institution,String id) {
        super(userName, name, institution,id);
    }


    public List<String> getCoursesEnrolled() {
        return coursesEnrolled;
    }

    public void setCoursesEnrolled(List<String> coursesEnrolled) {
        this.coursesEnrolled = coursesEnrolled;
    }

    public List<String> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(List<String> waitingList) {
        this.waitingList = waitingList;
    }
}
