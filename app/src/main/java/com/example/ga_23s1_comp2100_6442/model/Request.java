package com.example.ga_23s1_comp2100_6442.model;

import java.io.Serializable;

public class Request {
    private String sender;
    private String senderName;
    private String receiver;
    private String receiverName;
    private boolean isAccepted;
    private RequestType requestType;

    private String information;

    public Request() {
    }

    public Request(String sender, String receiver, RequestType requestType, String information, String senderName, String receiverName) {
        this.sender = sender;
        this.receiver = receiver;
        this.requestType = requestType;
        this.isAccepted = false;
        this.information = information;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }




    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}
