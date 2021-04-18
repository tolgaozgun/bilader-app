package com.breakdown.bilader.models;

public class Review {

    private User sentBy;
    private String content;


    public Review( User userName, String content ) {
        this.sentBy = userName;
        this.content = content;
    }

    public User getSentBy() {
        return sentBy;
    }

    public String getContent() {
        return content;
    }
}
