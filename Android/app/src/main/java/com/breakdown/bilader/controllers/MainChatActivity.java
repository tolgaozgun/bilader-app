package com.breakdown.bilader.controllers;

import android.app.Activity;

import com.breakdown.bilader.database.ChatDatabase;
import com.breakdown.bilader.database.DChat;

import java.util.List;

public class MainChatActivity extends Activity {


    // TODO: Implement method that displays all DCHat instances. On clicked,
    //  creates an intent to PrivateChatActivity with the chat id in the
    //  intent extras.
    //  To get values out of DChat use,
    //  DChat dChat;
    //  dChat.get("id") for example
    //  Check DChat class for available values.


    /**
     * Loads all the messages from the local database.
     *
     * @return List of DChat instances.
     */
    private List< DChat > loadChats() {
        ChatDatabase database;
        database = ChatDatabase.getInstance( this.getApplicationContext() );
        return database.chatDao().getAllChats();
    }
}



