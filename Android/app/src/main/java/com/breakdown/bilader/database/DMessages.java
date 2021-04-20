package com.breakdown.bilader.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class DMessages {

    @PrimaryKey
    @ColumnInfo(name="chatId")
    public String chatId;

    @ColumnInfo(name = "sender_id")
    public String senderId;

    @ColumnInfo(name = "receiver_id")
    public String receiverId;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "time")
    public Date time;

}
