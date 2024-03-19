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
    public MutableLiveData<String> schoolName = new MutableLiveData<>("");

    public MutableLiveData<String> roleName = new MutableLiveData<>("");

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
                if (result != null) {
                    loggedInUser.postValue(result);
                    loadUserSchool(result);
                    loadRoleName(result);
                }
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

    void loadUserSchool (User user) {
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

    void loadRoleName (User user) {
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

            }

            @Override
            public void onUI(String result) {

            }
        });
    }


}
