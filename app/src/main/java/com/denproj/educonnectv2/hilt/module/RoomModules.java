package com.denproj.educonnectv2.hilt.module;

import android.content.Context;
import android.util.Log;

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
    static AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        Log.d("Module", "Created");
        return Room.databaseBuilder(context, AppDatabase.class, "AppDatabase").fallbackToDestructiveMigration().build();
    }


    @Provides
    UserDao provideUserDao(AppDatabase database) {
        return database.getUserDao();
    }

}
