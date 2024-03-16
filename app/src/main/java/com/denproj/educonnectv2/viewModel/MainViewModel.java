package com.denproj.educonnectv2.viewModel;

import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.UITask;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel
{


    UserDao userDao;

    @Inject
    public MainViewModel (UserDao userDao) {
        this.userDao = userDao;
    }

    public <T> void login(UITask<T> uiTask) {

    }

    public <T> void register(UITask<T> uiTask) {

    }


}
