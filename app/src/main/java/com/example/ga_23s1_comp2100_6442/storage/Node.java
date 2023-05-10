package com.example.ga_23s1_comp2100_6442.storage;

import com.example.ga_23s1_comp2100_6442.model.Course;

public class Node {
    private int height;
    private Node left;
    private Node right;
    private Course course;

    public Node(Node left, Node right, Course course) {
        this.left = left;
        this.right = right;
        this.course = course;
        this.height = 1;
    }
    public Node(Course course) {
        this.course = course;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
