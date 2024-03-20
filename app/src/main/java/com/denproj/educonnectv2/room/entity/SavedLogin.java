package com.denproj.educonnectv2.room.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId")})
public class SavedLogin {

    @PrimaryKey(autoGenerate = true)
    public int saveId;

    public int userId;

    public SavedLogin(int userId) {
        this.userId = userId;
    }

    public SavedLogin() {
    }
}
