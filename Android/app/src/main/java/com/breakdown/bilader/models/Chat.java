package com.breakdown.bilader.models;

public class Chat {
    private User userSender;
    private User userReceiver;
    private final long id;
    private String lastMessage;

    public Chat(User userSender, User userReceiver, long id, String lastMessage){
        this.id = id;
        this.userReceiver = userReceiver;
        this.userSender = userSender;
        this.lastMessage = lastMessage;
    }

    public boolean sendMessage(Message message){
        // TODO: Send message to the Websocket server
        return false;
    }
}
