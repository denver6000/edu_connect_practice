package com.denproj.educonnectv2.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "sectionName", unique = true)})
public class Sections {

    @PrimaryKey(autoGenerate = true)
    public int sectionId;
    public String sectionName;

    public Sections(String sectionName) {
        this.sectionName = sectionName;
    }

    public Sections() {
    }

    @NonNull
    @Override
    public String toString() {
        return sectionName;
    }
}
