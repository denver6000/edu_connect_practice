package com.denproj.educonnectv2.room.entity;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Schools.class, parentColumns = "schoolId", childColumns = "schoolId")})
public class User implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int userId;
    public String firstName = "";
    public String lastName = "";
    public String middleName = "";
    public String email = "";
    public String password = "";

    public int schoolId;
    public int roleId;


    protected User(Parcel in) {
        userId = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        middleName = in.readString();
        email = in.readString();
        password = in.readString();
        schoolId = in.readInt();
        roleId = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(middleName);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeInt(schoolId);
        parcel.writeInt(roleId);
    }

    public User () {

    }
}
