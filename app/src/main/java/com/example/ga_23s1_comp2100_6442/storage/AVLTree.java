package com.example.ga_23s1_comp2100_6442.storage;

import com.example.ga_23s1_comp2100_6442.model.Course;
import com.google.gson.Gson;

import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;

public class AVLTree {

    private Node root;

    public void insert(Course course) {
        root = insertion(root, course);
    }
    private Node insertion(Node node, Course course) {
        if (node == null) {
            return new Node(course);
        }

        // insert by title
        Course finder = course;
        Course current = node.getCourse();
        int comparison = finder.compareTo(current);

        if (comparison > 0) {
            node.setRight(insertion(node.getRight(), course));

            int balanceFactor = getBalanceFactor(node);
            if (balanceFactor == -2) {
                Course rightChildTitle = node.getRight().getCourse();
                if (finder.compareTo(rightChildTitle) < 0) {
                    node = rotateRightLeft(node);
                } else {
                    node = rotateLeft(node);
                }
            }
        } else if (comparison < 0) {
            node.setLeft(insertion(node.getLeft(), course));
            int balanceFactor = getBalanceFactor(node);
            if (balanceFactor == 2) {
                Course leftChildTitle= node.getLeft().getCourse();
                if (finder.compareTo(leftChildTitle) < 0) {
                    node = rotateRight(node);
                } else {
                    node = rotateLeftRight(node);
                }
            }
        } else;
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
        return node;
    }

    public void deletion() {

    }

    public Node rotateLeft(Node node) {
        // doing left rotation
        Node rightNode = node.getRight();
        Node leftRightNode = rightNode.getLeft();
        node.setRight(leftRightNode);
        rightNode.setLeft(node);
        // update height for the affected node
        rightNode.setHeight(1 + Math.max(getHeight(rightNode.getRight()), getHeight(rightNode.getLeft())));
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
        return rightNode;
    }

    public Node rotateRight(Node node) {
        // doing right rotation
        Node leftNode = node.getLeft();
        Node rightLeftNode = leftNode.getRight();
        node.setLeft(rightLeftNode);
        leftNode.setRight(node);
        // update height for the affected node
        leftNode.setHeight(1 + Math.max(getHeight(leftNode.getLeft()), getHeight(leftNode.getRight())));
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
        return leftNode;
    }

    public Node rotateLeftRight(Node node) {
        node.setLeft(rotateLeft(node.getLeft()));
        return rotateRight(node);
    }
    public Node rotateRightLeft(Node node) {
        node.setRight(rotateRight(node.getRight()));
        return rotateLeft(node);
    }
    public int getHeight(Node node) {
        if (node == null) return -1;
        return node.getHeight();
    }

    public int getBalanceFactor(Node node) {
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    // in order traversal for tree visualisation
    public void inOrderTraversal() {
        System.out.println("start of traversal: ");
        inOrderTraversal(root);
    }
    private void inOrderTraversal(Node node) {
        if (node == null) return;
        inOrderTraversal(node.getLeft());
        System.out.println(node.getCourse().getTitle());
        inOrderTraversal(node.getRight());
    }
    public boolean search(Course course) {
        return search(root, course);
    }
    public boolean search(Node node, Course course) {
        if (node == null) return false;
        Course currentCourse = node.getCourse();
        if (course.compareTo(currentCourse) == 0) {
            return true;
        } else if (course.compareTo(currentCourse) > 0) {
            return search(node.getRight(), course);
        } else {
            return search(node.getLeft(), course);
        }
    }




}
