package com.denproj.educonnectv2.room.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public int userId;
    public String firstName = "";
    public String lastName = "";
    public String middleName = "";
    public String email = "";
    public String password = "";
    public int roleId;



}
