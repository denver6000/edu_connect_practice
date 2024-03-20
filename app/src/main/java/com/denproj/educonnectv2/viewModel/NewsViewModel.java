package com.denproj.educonnectv2.viewModel;

import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.News;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.QueryTask;
import com.denproj.educonnectv2.util.UITask;

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

    public void saveNews(UITask<Void> uiTask) {
        if (news.newsTitle.isEmpty() || news.newsDescription.isEmpty() || news.imageByteArray == null || news.schoolScope == 0) {
            uiTask.onFail("Empty Field");
        } else {
            AsyncRunner.runAsync(new QueryTask<Void>() {
                @Override
                public Void onTask() {
                    return userDao.addNews(news);
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
        }
    }

    public void getAllNews(int schoolId, UITask<List<News>> uiTask) {
        AsyncRunner.runAsync(new QueryTask<List<News>>() {
            @Override
            public List<News> onTask() {
                return userDao.getAllNews(schoolId);
            }

            @Override
            public void onSuccess(List<News> result) {

            }

            @Override
            public void onFail(String message) {
                uiTask.onFail(message);
            }

            @Override
            public void onUI(List<News> result) {
                uiTask.onSuccess(result);
            }
        });
    }
}
