package com.denproj.educonnectv2.room.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Sections.class, parentColumns = "sectionId", childColumns = "sectionId"),
        @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId")})
public class StudentWithSection {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public int sectionId;

    public StudentWithSection(int userId, int sectionId) {
        this.userId = userId;
        this.sectionId = sectionId;
    }

    public StudentWithSection() {
    }
}
