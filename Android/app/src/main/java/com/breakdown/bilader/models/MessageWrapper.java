package com.breakdown.bilader.models;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class MessageWrapper implements IMessage {
    private Message message;

    public MessageWrapper( Message message ) {
        this.message = message;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getText() {
        return message.getContent();
    }

    @Override
    public IUser getUser() {
        return new UserWrapper( message.getSentBy() );
    }

    @Override
    public Date getCreatedAt() {
        return null;
    }
}
