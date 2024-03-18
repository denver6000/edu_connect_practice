package com.denproj.educonnectv2.room.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Roles {

    @Ignore
    public static final String role_1 = "admin";
    @Ignore
    public static final String role_2 = "teacher";
    @Ignore
    public static final String role_3 = "student";

    @PrimaryKey(autoGenerate = true)
    public int roleId;

    public String roleName;

    public Roles(String roleName) {
        this.roleName = roleName;
    }

    public Roles() {
    }
}
