package com.breakdown.bilader.models;

public class Chat {
<<<<<<< Updated upstream
=======

    private User userSender;
    private User userReceiver;
    private final long id;
    private String lastMessage;

    /**
     * Constructor
     * @param userSender The User object of the sender
     * @param userReceiver The User object of the receiver
     * @param id The long value of current chat id.
     * @param lastMessage The String value of last transmitted message.
     */
    public Chat(User userSender, User userReceiver, long id, String lastMessage) {
        this.id = id;
        this.userReceiver = userReceiver;
        this.userSender = userSender;
        this.lastMessage = lastMessage;
    }

    /**
     * Upon called, sends a request to server to send a message to this chat
     * as the current user.
     * @param message Message object to be sent.
     * @return Boolean value whether it was successful.
     */
    public boolean sendMessage(User user, Message message) {
        // TODO: Change the user to something like sessionID, we want this
        // client to send messages only for themselves not on behalf of
        // someone else.
        // TODO: Call the method to send message to the Websocket server
        return false;
    }

    /**
     * Returns the User object of the sender.
     * @return User object of the sender.
     */
    public User getSender() {
        return userSender;
    }

    /**
     * Returns the User object of the receiver.
     * @return User object of the receiver.
     */
    public User getReceiver() {
        return userReceiver;
    }

    /**
     * Returns the String value of the last transmitted message.
     * @return String value of the last transmitted message.
     */
    public String getLastMessage() {
        return lastMessage;
    }

>>>>>>> Stashed changes
}
