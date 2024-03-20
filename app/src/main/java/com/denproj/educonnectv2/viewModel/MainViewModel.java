package com.denproj.educonnectv2.viewModel;

import android.content.Context;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.room.entity.SavedLogin;
import com.denproj.educonnectv2.room.entity.Schools;
import com.denproj.educonnectv2.room.entity.SharedPrefUtil;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.QueryTask;
import com.denproj.educonnectv2.util.UITask;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    public ObservableField<String> emailForLogin = new ObservableField<>("");

    public ObservableField<String> passwordForLogin = new ObservableField<>("");

    public ObservableField<String> confirmPassword = new ObservableField<>("");


    public ObservableField<String> schoolName = new ObservableField<>("");
    public User userForRegister = new User();

    UserDao userDao;

    @Inject
    public MainViewModel(UserDao userDao) {
        this.userDao = userDao;
    }

    public void login(UITask<User> uiTask) {
        if (emailForLogin.get().isEmpty() || passwordForLogin.get().isEmpty()) {
            uiTask.onFail("Empty Field");

        } else {
            AsyncRunner.runAsync(new QueryTask<User>() {
                @Override
                public User onTask() {
                    return userDao.loginUser(emailForLogin.get(), passwordForLogin.get());
                }

                @Override
                public void onSuccess(User result) {
                    userDao.saveLogin(new SavedLogin(result.userId));
                }

                @Override
                public void onFail(String message) {
                    uiTask.onFail(message);
                }

                @Override
                public void onUI(User result) {
                    if (result == null) {
                        uiTask.onFail("Incorrect email or password");
                    } else {
                        uiTask.onSuccess(result);
                    }
                }
            });
        }
    }


    public void register(UITask<Void> uiTask) {

        if (userForRegister.firstName.isEmpty() ||
                userForRegister.middleName.isEmpty() ||
                userForRegister.lastName.isEmpty() ||
                userForRegister.email.isEmpty() ||
                userForRegister.password.isEmpty() ||
                confirmPassword.get().isEmpty() ||
                schoolName.get().isEmpty()) {

            uiTask.onFail("Empty Field");
        } else if (userForRegister.password.equals(confirmPassword.get())) {
            AsyncRunner.runAsync(new QueryTask<Void>() {
                @Override
                public Void onTask() {
                    userForRegister.roleId = userDao.getRoleIdFromRoleName(Roles.role_1);
                    userForRegister.schoolId = registerSchoolAndGetSchoolId(schoolName.get());
                    return userDao.registerUser(userForRegister);
                }

                @Override
                public void onSuccess(Void result) {

                }

                @Override
                public void onFail(String message) {
                    uiTask.onFail(message);
                }

                @Override
                public void onUI(Void result) {
                    uiTask.onSuccess(result);
                }
            });
        } else {
            uiTask.onFail("Password Mismatch");
        }
    }

    public void checkAndLoadSaveLogin(UITask<SavedLogin> uiTask) {
        AsyncRunner.runAsync(new QueryTask<SavedLogin>() {
            @Override
            public SavedLogin onTask() {
                return userDao.getRecentlySavedLogin();
            }

            @Override
            public void onSuccess(SavedLogin result) {

            }

            @Override
            public void onFail(String message) {
                uiTask.onFail(message);
            }

            @Override
            public void onUI(SavedLogin result) {
                if (result == null) {
                    uiTask.onFail("No Saved Login");
                } else {
                    uiTask.onSuccess(result);
                }
            }
        });
    }

    public int registerSchoolAndGetSchoolId(String schoolName) {
        userDao.registerSchool(new Schools(schoolName));

        return userDao.selectSchoolIdByName(schoolName);
    }

    public void attemptToRegisterRoles(Context context) {
        SharedPrefUtil.runCodeOnce(context, userDao);
    }


}
