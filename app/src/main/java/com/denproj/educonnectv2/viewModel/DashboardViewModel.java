package com.denproj.educonnectv2.viewModel;

import android.util.Log;

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
    public MutableLiveData<String> schoolName = new MutableLiveData<>("");
    public MutableLiveData<String> roleName = new MutableLiveData<>("");

    UserDao userDao;
    @Inject
    public DashboardViewModel (UserDao userDao) {
        this.userDao = userDao;
    }

    public DashboardViewModel () {

    }



    public void loadUserSchool (User user) {
        AsyncRunner.runAsync(new QueryTask<String>() {
            @Override
            public String onTask() {
                return userDao.getSchoolNameById(user.schoolId);
            }

            @Override
            public void onSuccess(String result) {
                schoolName.postValue(result);
            }

            @Override
            public void onFail(String message) {
            }

            @Override
            public void onUI(String result) {
            }
        });
    }

    public void loadRoleName (User user, UITask<String> uiTask) {
        AsyncRunner.runAsync(new QueryTask<String>() {
            @Override
            public String onTask() {
                return userDao.getRoleNameById(user.roleId);
            }

            @Override
            public void onSuccess(String result) {
                roleName.postValue(result);
            }

            @Override
            public void onFail(String message) {
                uiTask.onFail(message);
            }

            @Override
            public void onUI(String result) {
                uiTask.onSuccess(result);
            }
        });
    }


}
