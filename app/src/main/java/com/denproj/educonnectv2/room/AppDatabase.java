package com.denproj.educonnectv2.room;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.Group;
import com.denproj.educonnectv2.room.entity.News;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.room.entity.SavedLogin;
import com.denproj.educonnectv2.room.entity.Schools;
import com.denproj.educonnectv2.room.entity.Sections;
import com.denproj.educonnectv2.room.entity.Student;
import com.denproj.educonnectv2.room.entity.StudentAndGroup;
import com.denproj.educonnectv2.room.entity.StudentWithSection;
import com.denproj.educonnectv2.room.entity.User;

@Database(
        entities = {Roles.class, User.class, Schools.class, News.class, SavedLogin.class, Sections.class, StudentWithSection.class, Group.class, StudentAndGroup.class},
        views = {Student.class},

        version = 14)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();


}
