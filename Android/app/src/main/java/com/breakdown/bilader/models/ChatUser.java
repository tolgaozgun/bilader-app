package com.breakdown.bilader.models;

/**
 * A class that holds the chats in the Main Chat Screen. It shows the last
 * message in the chat box of the user has been chated.
 *
 * @author breakDown
 * @version 13.04.2021
 */

public class ChatUser extends User implements Comparable {

    String lastMessage;
    String chatId;
    long lastMessageDate;

    /**
     * Constructor
     *
     * @param name            String value of the name.
     * @param avatar          String value of the avatar.
     * @param id              String value of the id.
     * @param lastMessage     String value of the last message.
     * @param chatId          String value of the chatId
     * @param lastMessageDate long value of the lastMessageDate
     */
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

    @Override
    public int compareTo( Object o ) {
        if ( o instanceof ChatUser ) {
            Long curMessageTime = new Long( lastMessageDate );
            return curMessageTime.compareTo( ( ( ChatUser ) o ).getLastMessageDate() );
        }
        return 0;
    }
}
