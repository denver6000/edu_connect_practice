package com.denproj.educonnectv2.viewModel;

import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.SavedLogin;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.QueryTask;
import com.denproj.educonnectv2.util.UITask;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SplashScreenViewModel extends ViewModel {

    UserDao userDao;

    @Inject
    public SplashScreenViewModel(UserDao userDao) {

        this.userDao = userDao;
    }

    public SplashScreenViewModel() {


    }

    public void checkAndLoadSavedLogin(UITask<User> uiTask) {
        AsyncRunner.runAsync(new QueryTask<User>() {

            @Override
            public User onTask() {
                SavedLogin savedLogin = userDao.getRecentlySavedLogin();
                return userDao.selectUserById(savedLogin.userId);
            }

            @Override
            public void onSuccess(User result) {

            }

            @Override
            public void onFail(String message) {
                uiTask.onFail(message);
            }

            @Override
            public void onUI(User result) {
                if (result == null) {
                    uiTask.onFail("Uer not found");
                } else {
                    uiTask.onSuccess(result);
                }
            }
        });
    }

}
