package com.denproj.educonnectv2.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.News;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.room.entity.SavedLogin;
import com.denproj.educonnectv2.room.entity.Schools;
import com.denproj.educonnectv2.room.entity.User;

@Database(exportSchema = false, entities = {Roles.class, User.class, Schools.class, News.class, SavedLogin.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();


}
