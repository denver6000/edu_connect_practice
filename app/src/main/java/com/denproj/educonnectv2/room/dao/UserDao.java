package com.denproj.educonnectv2.room.dao;


import android.media.metrics.Event;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.denproj.educonnectv2.room.entity.Events;
import com.denproj.educonnectv2.room.entity.Group;
import com.denproj.educonnectv2.room.entity.News;
import com.denproj.educonnectv2.room.entity.Resource;
import com.denproj.educonnectv2.room.entity.ResourceFile;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.room.entity.SavedLogin;
import com.denproj.educonnectv2.room.entity.Schools;
import com.denproj.educonnectv2.room.entity.Sections;
import com.denproj.educonnectv2.room.entity.Student;
import com.denproj.educonnectv2.room.entity.StudentAndGroup;
import com.denproj.educonnectv2.room.entity.StudentWithSection;
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

    @Query("SELECT * FROM Sections")
    List<Sections> getAllSection();


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


    @Insert
    Void registerSection(Sections sections);

    @Query("SELECT sectionId FROM Sections WHERE sectionName=:name LIMIT 1")
    int getSectionIdWithName(String name);

    @Query("SELECT EXISTS (SELECT * FROM Sections WHERE sectionName=:name)")
    boolean checkIfSectionExistByName(String name);

    @Insert
    Void bindStudentWithSection(StudentWithSection studentWithSection);

    @Query("SELECT userId FROM User ORDER BY userId DESC LIMIT 1")
    int getMostRecentlyInsertedRow ();

    @Query("SELECT * FROM Student")
    List<Student> getStudentWithSection();

    @Insert
    void addGroup(Group group);

    @Query("SELECT groupId FROM `Group` WHERE groupName=:groupName LIMIT 1")
    int getGroupIdByName(String groupName);

    @Insert
    Void insertStudentGroup(StudentAndGroup studentAndGroup);

    @Query("SELECT * FROM `Group`")
    List<Group> getAllGroup();

    @Query("SELECT EXISTS (SELECT * FROM Roles LIMIT 3)")
    boolean checkIfRolesExist();

    @Insert
    Void insertEvent(Events events);

    @Query("SELECT * FROM Events")
    List<Events> getAllEvents();

    @Insert
    long insertResource(Resource resource);

    @Insert
    Void insertResourceFile(ResourceFile resourceFile);

    @Query("SELECT * FROM Resource")
    List<Resource> getAllResource();

    @Query("SELECT filePath FROM ResourceFile WHERE resourceId = :resourceId")
    List<String> getAllFilesFromAResourceId(int resourceId);

}
