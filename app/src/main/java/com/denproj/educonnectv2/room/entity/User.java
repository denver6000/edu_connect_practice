package com.denproj.educonnectv2.room.entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Schools.class, parentColumns = "schoolId", childColumns = "schoolId"))
public class User {

    @PrimaryKey(autoGenerate = true)
    public int userId;
    public String firstName = "";
    public String lastName = "";
    public String middleName = "";
    public String email = "";
    public String password = "";

    public int schoolId;
    public int roleId;



}
