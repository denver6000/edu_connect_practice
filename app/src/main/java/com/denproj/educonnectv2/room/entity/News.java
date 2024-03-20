package com.denproj.educonnectv2.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class News {
    @PrimaryKey(autoGenerate = true)
    public int newsId;

    public String newsTitle = "";
    public String newsDescription = "";
    public int schoolScope = 0;
    public byte[] imageByteArray = null;
}
