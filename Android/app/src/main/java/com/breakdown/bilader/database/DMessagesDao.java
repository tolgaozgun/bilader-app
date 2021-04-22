package com.breakdown.bilader.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DMessagesDao {


    @Query( "SELECT * FROM DMessages" )
    List<DMessages> getAllMessages();

    @Insert
    void insertMessage(DMessages... chats);

    @Delete
    void delete(DMessages chat);

}