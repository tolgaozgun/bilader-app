package com.breakdown.bilader.models;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageWrapper implements IMessage {
    private Message message;

    public MessageWrapper( Message message ) {
        this.message = message;
    }

    @Override
    public String getId() {
        return message.getMessageId();
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
    public Date getCreatedAt() { return new Date( message.getMessageTime() );
    }
}
