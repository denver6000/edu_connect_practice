package com.denproj.educonnectv2.room.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.denproj.educonnectv2.room.entity.News;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.room.entity.SavedLogin;
import com.denproj.educonnectv2.room.entity.Schools;
import com.denproj.educonnectv2.room.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("DELETE FROM SavedLogin")
    Void clearSavedLogin();

    @Insert
    Void registerUser(User user);

    @Query("SELECT * FROM User WHERE email =:email AND password=:password")
    User loginUser(String email, String password);

    @Insert
    Void registerRole(Roles roles);

    @Insert
    Void registerSchool(Schools schools);


    @Query("SELECT * FROM Schools WHERE schoolName = :schoolName LIMIT 1")
    Schools doesSchoolExist(String schoolName);

    @Query("SELECT schoolId FROM Schools WHERE schoolName=:schoolName")
    int selectSchoolIdByName(String schoolName);

    @Query("SELECT * FROM User WHERE userId = :userId LIMIT 1")
    User selectUserById(int userId);

    @Query("SELECT roleId FROM Roles WHERE roleName = :roleName LIMIT 1")
    int getRoleIdFromRoleName(String roleName);

    @Query("SELECT roleName FROM Roles WHERE roleId=:roleId")
    String getRoleNameById(int roleId);

    @Query("SELECT schoolName FROM Schools WHERE schoolId = :schoolId")
    String getSchoolNameById(int schoolId);

    @Insert
    Void addNews(News news);

    @Query("SELECT * FROM News WHERE schoolScope = :schoolId")
    List<News> getAllNews(int schoolId);


    @Insert
    Void saveLogin(SavedLogin savedLogin);


    @Query("SElECT * FROM Savedlogin LIMIT 1")
    SavedLogin getRecentlySavedLogin();


}
