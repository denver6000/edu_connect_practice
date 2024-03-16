package com.denproj.educonnectv2.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.room.entity.User;

@Database(exportSchema = false, entities = {Roles.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

}
