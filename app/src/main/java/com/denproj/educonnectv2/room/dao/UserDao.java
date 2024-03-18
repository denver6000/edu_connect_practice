package com.denproj.educonnectv2.room.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.room.entity.Schools;
import com.denproj.educonnectv2.room.entity.User;

@Dao
public interface UserDao {

    @Insert
    Void registerUser(User user);

    @Query("SELECT * FROM User WHERE email =:email AND password=:password")
    User loginUser(String email, String password);

    @Insert
    Void registerRole(Roles roles);

    @Insert
    Void registerSchool(Schools schools);

    @Query("SELECT schoolId FROM Schools WHERE schoolName=:schoolName")
    int selectSchoolIdByName(String schoolName);

    @Query("SELECT * FROM User WHERE userId = :userId LIMIT 1")
    User selectUserById(int userId);

    @Query("SELECT roleId FROM Roles WHERE roleName = :roleName LIMIT 1")
    int getRoleIdFromRoleName(String roleName);



}
