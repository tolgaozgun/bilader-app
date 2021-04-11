package com.breakdown.bilader.models;

import java.util.Date;

public class Message {

    private long messageTime;
    private User sentBy;
    private Date date;
    private String content;

    /**
     * Constructor
     *
     * @param date        Date instance of the message date.
     * @param messageTime long value of message time.
     * @param sentBy      User instance of sender.
     * @param content     String value of the content.
     */
    public Message( Date date, long messageTime, User sentBy, String content ) {
        this.messageTime = messageTime;
        this.sentBy = sentBy;
        this.date = date;
        this.content = content;
    }

    /**
     * Returns the long value of the message time.
     *
     * @return Long value of message time.
     */
    public long getMessageTime() {
        return messageTime;
    }

    /**
     * Returns the User instance of sender.
     *
     * @return User instance of sender.
     */
    public User getSentBy() {
        return sentBy;
    }

    /**
     * Returns the Date instance of date.
     *
     * @return Date instance of date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the String value of content.
     *
     * @return String value of content.
     */
    public String getContent() {
        return content;
    }
}
