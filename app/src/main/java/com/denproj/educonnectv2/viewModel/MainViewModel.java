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







    public void attemptToRegisterRoles(Context context) {
        SharedPrefUtil.runCodeOnce(context, userDao);
    }


}
