package com.denproj.educonnectv2.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Resource {

    @PrimaryKey(autoGenerate = true)
    public int resourceId;

    public String resourceName;

    public Resource(String resourceName) {
        this.resourceName = resourceName;
    }
}
