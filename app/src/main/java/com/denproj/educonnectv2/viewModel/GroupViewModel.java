package com.denproj.educonnectv2.viewModel;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.Group;
import com.denproj.educonnectv2.room.entity.Sections;
import com.denproj.educonnectv2.room.entity.Student;
import com.denproj.educonnectv2.room.entity.StudentAndGroup;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.QueryTask;
import com.denproj.educonnectv2.util.UITask;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GroupViewModel extends ViewModel {

    UserDao userDao;

    public MutableLiveData<List<Student>> studentList = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<List<Sections>> sectionsList = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<List<Student>> selectedStudents = new MutableLiveData<>(new ArrayList<>());


    public ObservableField<String> groupName = new ObservableField<>("");

    @Inject
    public GroupViewModel(UserDao userDao) {
        this.userDao = userDao;
    }

    public GroupViewModel() {
    }

    public void loadAllSection(UITask<List<Sections>> uiTask) {
        AsyncRunner.runAsync(new QueryTask<List<Sections>>() {
            @Override
            public List<Sections> onTask() throws Exception {
                return userDao.getAllSection();
            }

            @Override
            public void onSuccess(List<Sections> result) {

            }

            @Override
            public void onFail(String message) {
                uiTask.onFail(message);
            }

            @Override
            public void onUI(List<Sections> result) {
                uiTask.onSuccess(result);
            }
        });
    }

    public void addStudentsToGroup(UITask<Void> uiTask) {
        if (groupName.get() != null && !groupName.get().isEmpty()) {
            AsyncRunner.runAsync(new QueryTask<Void>() {
                @Override
                public Void onTask() throws Exception {
//                    userDao.addGroup(new Group(groupName.get()));
//
//                    int groupId = userDao.getGroupIdByName(groupName.get());

                    Log.d("Adapter", "GROUP VIEW MODEL");
                    for (Student student : studentList.getValue()) {

                        Log.d("Adapter", "GROUP VIEW MODEL");
                        if (student.isSelected) {
//                            userDao.insertStudentGroup(new StudentAndGroup(student.userId, groupId));

                            Log.d("Adapter", student.email + " is to be added.");
                        }
                    }
                    return null;
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
            uiTask.onFail("Please enter group name");
        }
    }

    public void loadAllStudents() {
        AsyncRunner.runAsync(new QueryTask<List<Student>>() {
            @Override
            public List<Student> onTask() throws Exception {
                return userDao.getStudentWithSection();
            }

            @Override
            public void onSuccess(List<Student> result) {
                Log.d("GroupViewModel", result.size() + "");
            }

            @Override
            public void onFail(String message) {
                Log.d("Error", message);
            }

            @Override
            public void onUI(List<Student> result) {
                studentList.setValue(result);
            }
        });
    }

    public void loadAllGroup(UITask<List<Group>> uiTask) {
        AsyncRunner.runAsync(new QueryTask<List<Group>>() {
            @Override
            public List<Group> onTask() throws Exception {
                return userDao.getAllGroup();
            }

            @Override
            public void onSuccess(List<Group> result) {

            }

            @Override
            public void onFail(String message) {
                uiTask.onFail(message);
            }

            @Override
            public void onUI(List<Group> result) {
                uiTask.onSuccess(result);
            }
        });
    }

    public void createGroup() {
        userDao.addGroup(new Group(groupName.get()));
    }





}
