package com.breakdown.bilader.models;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class Message implements IMessage {

    private long messageTime;
    private User sentBy;
    private String content;
    private String messageId;

    /**
     * Constructor
     *
     * @param messageTime long value of message time.
     * @param sentBy      User instance of sender.
     * @param content     String value of the content.
     * @param messageId   String id of the message.
     */
    public Message( long messageTime, User sentBy, String content,
                    String messageId ) {
        this.messageTime = messageTime;
        this.sentBy = sentBy;
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
     * Returns the String value of the message id
     *
     * @return String value of message id.
     */
    @Override
    public String getId() {
        return messageId;
    }

    /**
     * Returns the String value of the message content.
     *
     * @return String value of message content.
     */
    @Override
    public String getText() {
        return content;
    }

    /**
     * Returns the IUser value of the sender of the message.
     *
     * @return IUser (Interface) value of the sender.
     */
    @Override
    public IUser getUser() {
        return sentBy;
    }

    /**
     * Returns the Date value of the message time.
     *
     * @return Date (Object) value of message time.
     */
    @Override
    public Date getCreatedAt() {
        return new Date( messageTime );
    }

    /**
     * Sets the time of the message
     *
     * @param messageTime  long value of the price.
     */
    public void setMessageTime( long messageTime ) {
        this.messageTime = messageTime;
    }

    /**
     * Sets the sender of the message
     *
     * @param sentBy  User (Object) value of the sender.
     */
    public void setSentBy( User sentBy ) {
        this.sentBy = sentBy;
    }

    //public void setDate( Date date ) { this.date = date; }

    /**
     * Sets the content of the message
     *
     * @param content  String value of the content.
     */
    public void setContent( String content ) {
        this.content = content;
    }

    /**
     * Sets the id of the message
     *
     * @param messageId  String value of the message.
     */
    public void setMessageId( String messageId ) {
        this.messageId = messageId;
    }
}
