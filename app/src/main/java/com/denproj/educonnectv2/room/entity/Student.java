package com.denproj.educonnectv2.room.entity;

import androidx.annotation.Nullable;
import androidx.room.DatabaseView;
import androidx.room.Entity;
import androidx.room.Ignore;

@DatabaseView("" +
        "SELECT * FROM User " +
        "INNER JOIN StudentWithSection ON User.userId = StudentWithSection.userId " +
        "INNER JOIN Sections ON Sections.sectionId = StudentWithSection.sectionId " +
        "WHERE User.roleId=3")
public class Student {


    public int userId;
    public String firstName;
    public String middleName;
    public String lastName;

    public String email;
    public String sectionName;
    public String sectionId;

    @Ignore
    public boolean isSelected = false;

    @Ignore
    public boolean showInList = true;

    @Override
    public boolean equals(@Nullable Object obj) {
        Student student = (Student) obj;
        assert student != null;
        return this.userId == student.userId;
    }
}
