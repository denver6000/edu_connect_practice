package com.denproj.educonnectv2.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Group {
    @PrimaryKey(autoGenerate = true)
    public int groupId;

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group() {
    }

    public String groupName;
}
