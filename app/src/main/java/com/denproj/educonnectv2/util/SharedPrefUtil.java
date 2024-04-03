package com.denproj.educonnectv2.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.QueryTask;

public class SharedPrefUtil {

    private static final String PREF_NAME = "AppPrefs";
    private static final String KEY_IS_CODE_RAN = "codeRunState";



    public static void runCodeOnce(UserDao userDao) {
        AsyncRunner.runAsync(new QueryTask<Void>() {
            @Override
            public Void onTask() {
                if (!userDao.checkIfRolesExist()) {
                    userDao.registerRole(new Roles(Roles.role_1));

                    userDao.registerRole(new Roles(Roles.role_2));

                    userDao.registerRole(new Roles(Roles.role_3));
                }

                return null;
            }

            @Override
            public void onSuccess(Void result) {

            }

            @Override
            public void onFail(String message) {

            }

            @Override
            public void onUI(Void result) {

            }
        });
    }

}
