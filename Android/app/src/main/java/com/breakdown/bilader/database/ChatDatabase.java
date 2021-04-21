package com.breakdown.bilader.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database( entities = { DChat.class, DMessages.class }, version = 1 )
public abstract class ChatDatabase extends RoomDatabase {

    public abstract DChatDao chatDao();

    private static ChatDatabase instance;

    public static ChatDatabase getInstance( Context context ) {
        if ( instance == null ) {
            instance = Room.databaseBuilder( context.getApplicationContext(),
                    ChatDatabase.class, "chat-database" ).allowMainThreadQueries().build();
        }
        return instance;
    }
}
