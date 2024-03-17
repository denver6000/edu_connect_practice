package com.denproj.educonnectv2.room.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.denproj.educonnectv2.room.entity.User;

@Dao
public interface UserDao {

    @Insert
    Void registerUser(User user);

    @Query("SELECT * FROM User WHERE email =:email AND password=:password")
    User loginUser(String email, String password);

}
