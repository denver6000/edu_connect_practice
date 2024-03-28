package com.denproj.educonnectv2.hilt.module;

import android.content.Context;

import androidx.room.Room;

import com.denproj.educonnectv2.room.AppDatabase;
import com.denproj.educonnectv2.room.dao.UserDao;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RoomModules {

    @Provides
    AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "AppDatabase").fallbackToDestructiveMigration().createFromAsset("database/AppDatabase-Roles.sql").build();
    }


    @Provides
    UserDao provideUserDao(AppDatabase database) {
        return database.getUserDao();
    }

}
