package com.denproj.educonnectv2.room.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"schoolName"}, unique = true)})
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
