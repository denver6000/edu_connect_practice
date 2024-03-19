package com.denproj.educonnectv2.viewModel;

import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.News;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NewsViewModel extends ViewModel {


    UserDao userDao;
    List<News> newsList;

    public News news = new News();

    @Inject
    public NewsViewModel(UserDao userDao) {
        this.userDao = userDao;
    }

    public NewsViewModel () {

    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
