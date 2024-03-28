package com.denproj.educonnectv2.room.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, childColumns = "userId", parentColumns = "userId"),
        @ForeignKey(entity = Group.class, childColumns = "groupId", parentColumns = "groupId")})
public class StudentAndGroup {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public int groupId;

    public StudentAndGroup(int userId, int groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}
