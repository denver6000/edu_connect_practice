package com.denproj.educonnectv2.viewModel;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.Events;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.FilesUtil;
import com.denproj.educonnectv2.util.QueryTask;
import com.denproj.educonnectv2.util.UITask;

import java.util.List;
import java.util.UUID;


import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CalendarViewModel extends ViewModel {

    UserDao userDao;

    public MutableLiveData<Uri> uriLiveData = new MutableLiveData<>(null);

    public MutableLiveData<Long> startTimeMs = new MutableLiveData<>(-1L);
    public MutableLiveData<Long> endTimeMs = new MutableLiveData<>(-1L);

    public MutableLiveData<Long> startDayInEpoch = new MutableLiveData<>(-1L);
    public MutableLiveData<Long> endDayInEpoch = new MutableLiveData<>(-1L);

    public ObservableField<String> eventName = new ObservableField<>("");
    public ObservableField<String> eventDesc = new ObservableField<>("");

    @Inject
    public CalendarViewModel(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addEvent(Context context, UITask<String> uiTask) {

        long startDateInEpoch_ = this.startDayInEpoch.getValue();
        long endDateInEpoch_ = this.endDayInEpoch.getValue();

        long endTimeInMs_ = this.endTimeMs.getValue();
        long startTimeInMs_ = this.startTimeMs.getValue();

        String eventName_ = this.eventName.get();
        String eventDesc_ = this.eventDesc.get();
        if (uriLiveData.getValue() != null &&
                startDateInEpoch_ != -1L &&
                endDateInEpoch_ != -1L &&
                endTimeInMs_ != -1 &&
                startTimeInMs_ != -1 &&
                !eventName_.isEmpty() &&
                !eventDesc_.isEmpty()
        ) {


            if (endDateInEpoch_ > startDateInEpoch_ && endTimeInMs_ > startTimeInMs_) {
                AsyncRunner.runAsync(new QueryTask<String>() {
                    @Override
                    public String onTask() throws Exception {
                        String imageName = UUID.randomUUID().toString() + ".jpeg";
                        return FilesUtil.moveImageToInternalStorage(FilesUtil.EVENT_POSTER_PATH, imageName, uriLiveData.getValue(), context);
                    }

                    @Override
                    public void onSuccess(String result) {
                        userDao.insertEvent(new Events(eventName_, eventDesc_, startDateInEpoch_, endDateInEpoch_, startTimeInMs_, endTimeInMs_, result));
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
            } else {
                uiTask.onFail("Invalid date or time");
            }
        } else {
            uiTask.onFail("Empty Fields");
        }
    }

    public void getAllEvents(UITask<List<Events>> uiTask) {
        AsyncRunner.runAsync(new QueryTask<List<Events>>() {
            @Override
            public List<Events> onTask() throws Exception {
                return userDao.getAllEvents();
            }

            @Override
            public void onSuccess(List<Events> result) {

            }

            @Override
            public void onFail(String message) {
                uiTask.onFail(message);
            }

            @Override
            public void onUI(List<Events> result) {
                uiTask.onSuccess(result);
            }
        });
    }
}
