package com.breakdown.bilader.models;

public class ChatUser extends User {

    String lastMessage;
    String chatId;
    long lastMessageDate;

    public ChatUser( String name, String avatar, String id,
                     String lastMessage, String chatId, long lastMessageDate ) {
        super( name, avatar, id );
        this.lastMessage = lastMessage;
        this.chatId = chatId;
        this.lastMessageDate = lastMessageDate;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getChatId() {
        return chatId;
    }

    public long getLastMessageDate() {
        return lastMessageDate;
    }
}
