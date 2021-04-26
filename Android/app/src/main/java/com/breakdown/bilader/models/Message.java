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

    @Override
    public String getId() {
        return messageId;
    }

    @Override
    public String getText() {
        return content;
    }

    @Override
    public IUser getUser() {
        return sentBy;
    }

    @Override
    public Date getCreatedAt() {
        return new Date( messageTime );
    }

    public void setMessageTime( long messageTime ) {
        this.messageTime = messageTime;
    }

    public void setSentBy( User sentBy ) {
        this.sentBy = sentBy;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public void setMessageId( String messageId ) {
        this.messageId = messageId;
    }
}
