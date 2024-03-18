package com.denproj.educonnectv2.viewModel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.QueryTask;
import com.denproj.educonnectv2.util.UITask;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DashboardViewModel extends ViewModel {

    public MutableLiveData<User> loggedInUser = new MutableLiveData<>(null);
    UserDao userDao;
    @Inject
    public DashboardViewModel (UserDao userDao) {
        this.userDao = userDao;
    }

    public DashboardViewModel () {

    }

    public void getUser(int userId, UITask<User> userUITask) {
        AsyncRunner.runAsync(new QueryTask<User>() {
            @Override
            public User onTask() {
                return userDao.selectUserById(userId);
            }

            @Override
            public void onSuccess(User result) {
                loggedInUser.postValue(result);
            }

            @Override
            public void onFail(String message) {
                userUITask.onFail(message);
            }

            @Override
            public void onUI(User result) {
                userUITask.onSuccess(result);
            }
        });
    }




}
