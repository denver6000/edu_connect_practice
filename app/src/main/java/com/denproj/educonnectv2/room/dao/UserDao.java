package com.denproj.educonnectv2.room.dao;


import androidx.room.Dao;
import androidx.room.Insert;

import com.denproj.educonnectv2.room.entity.User;

@Dao
public interface UserDao {

    @Insert
    void registerUser(User user);

}
