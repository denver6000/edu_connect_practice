package com.denproj.educonnectv2.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Schools {

    @PrimaryKey(autoGenerate = true)
    public int schoolId;

     public String schoolName;

    public Schools(String schoolName) {
        this.schoolName = schoolName;
    }

    public Schools() {
    }
}
