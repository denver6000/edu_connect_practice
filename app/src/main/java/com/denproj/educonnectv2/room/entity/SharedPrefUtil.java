package com.denproj.educonnectv2.room.entity;

import android.content.Context;
import android.content.SharedPreferences;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.QueryTask;

public class SharedPrefUtil {

    private static final String PREF_NAME = "AppPrefs";
    private static final String KEY_IS_CODE_RAN = "codeRunState";



    public static void runCodeOnce(Context context, UserDao userDao) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean codeExecuted = sharedPreferences.getBoolean(KEY_IS_CODE_RAN, false);
        if (!codeExecuted) {
            AsyncRunner.runAsync(new QueryTask<Void>() {
                @Override
                public Void onTask() {
                    userDao.registerRole(new Roles(Roles.role_1));

                    userDao.registerRole(new Roles(Roles.role_2));

                    userDao.registerRole(new Roles(Roles.role_3));

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

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_IS_CODE_RAN, true);
            editor.apply();
        }
    }

}
