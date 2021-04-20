package com.breakdown.bilader.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DChatDao {


    @Query( "SELECT * FROM dchat" )
    List<DChat> getAllChats();

    @Insert
    void insertChat(DChat... chats);

    @Delete
    void delete(DChat chat);

}
