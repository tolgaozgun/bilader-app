package com.breakdown.bilader.models;

import java.util.Date;

public class Message {

    private long messageTime;
    private User sentBy;
    private Date date;
    private String content;
    private String messageId;

    /**
     * Constructor
     *
     * @param date        Date instance of the message date.
     * @param messageTime long value of message time.
     * @param sentBy      User instance of sender.
     * @param content     String value of the content.
     * @param messageId   String id of the message.
     */
    public Message( Date date, Long messageTime, User sentBy, String content,
                    String messageId ) {
        this.messageTime = messageTime;
        this.sentBy = sentBy;
        this.date = date;
        this.content = content;
        this.messageId = messageId;
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

    /**
     * Returns the String value of id.
     *
     * @return String value of message id.
     */
    public String getMessageId() { return messageId;
    }
}
