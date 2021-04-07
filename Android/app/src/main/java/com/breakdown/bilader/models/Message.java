package com.breakdown.bilader.models;

public class Message {

    private Date messageDate;
    private long messageTime;
    private User sentBy;
    private Date date;
    private String content;

    public Message(Date messageDate, long messageTime, User sentBy, Date date, String content) {
        this.messageDate = messageDate;
        this.messageTime = messageTime;
        this.sentBy = sentBy;
        this.date = date;
        this.content = content;
    }
}
