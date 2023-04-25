package com.example.ga_23s1_comp2100_6442.model;

public class Request {
    private User sender;
    private User receiver;
    private boolean isAccepted;
    private RequestType requestType;

    private String information;

    public Request() {
    }

    public Request(User sender, User receiver, RequestType requestType, String information) {
        this.sender = sender;
        this.receiver = receiver;
        this.requestType = requestType;
        this.isAccepted = false;
        this.information = information;
    }

    public void send() {
        sender.getRequestsSent().add(this);
        receiver.getRequestsGet().add(this);
    }

    public void accept() {
        if (requestType == RequestType.Follow) {
            sender.following.add(receiver);
            receiver.followers.add(sender);
            setAccepted(true);
        } else if (requestType == RequestType.JoinCourse) {
            Lecturer lecturer = (Lecturer) receiver;
            lecturer.addStudentToCourse((Student) sender, information);
            setAccepted(true);
        }
    }

    public void deny() {

    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public enum RequestType {
        Follow, JoinCourse
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
