package com.breakdown.bilader.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DChat {

    @PrimaryKey
    @ColumnInfo(name="id")
    public String id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "avatar_url")
    public String avatarUrl;




}
