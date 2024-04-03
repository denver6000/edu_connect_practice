package com.denproj.educonnectv2.viewModel;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.room.entity.Schools;
import com.denproj.educonnectv2.room.entity.Sections;
import com.denproj.educonnectv2.room.entity.StudentWithSection;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.QueryTask;
import com.denproj.educonnectv2.util.UITask;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {

    UserDao userDao;

    public MutableLiveData<User> loggedInUser = new MutableLiveData<>(null);
    public MutableLiveData<List<Sections>> sectionsList = new MutableLiveData<>();

    public MutableLiveData<Integer> selectedSectionIndex = new MutableLiveData<>(0);
    public MutableLiveData<Sections> selectedSection = new MutableLiveData<>(null);


    @Inject
    public RegisterViewModel(UserDao userDao) {
        this.userDao = userDao;
    }

    public RegisterViewModel() {

    }

    public User userForRegister = new User();

    public ObservableField<String> confirmPassword = new ObservableField<>("");
    public ObservableField<String> schoolName = new ObservableField<>("");

    public int registerSchoolAndGetSchoolId(String schoolName) {
        userDao.registerSchool(new Schools(schoolName));
        return userDao.selectSchoolIdByName(schoolName);
    }

    public void registerWithRole(String roleName, int schoolId, UITask<Void> uiTask) {

        if (userForRegister.firstName.isEmpty() ||
                userForRegister.middleName.isEmpty() ||
                userForRegister.lastName.isEmpty() ||
                userForRegister.email.isEmpty() ||
                userForRegister.password.isEmpty() ||
                confirmPassword.get().isEmpty()) {

            uiTask.onFail("Empty Field");
        } else if (userForRegister.password.equals(confirmPassword.get())) {
            AsyncRunner.runAsync(new QueryTask<Void>() {
                @Override
                public Void onTask() {
                    userForRegister.roleId = userDao.getRoleIdFromRoleName(roleName);
                    userForRegister.schoolId = schoolId;
                    return userDao.registerUser(userForRegister);
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
            uiTask.onFail("Password Mismatch");
        }
    }

    public void registerWithStudent (String roleName, int schoolId, UITask<Void> uiTask) {

        if (userForRegister.firstName.isEmpty() ||
                userForRegister.middleName.isEmpty() ||
                userForRegister.lastName.isEmpty() ||
                userForRegister.email.isEmpty() ||
                userForRegister.password.isEmpty() ||
                confirmPassword.get().isEmpty()) {

            uiTask.onFail("Empty Field");
        } else if (userForRegister.password.equals(confirmPassword.get())) {
            AsyncRunner.runAsync(new QueryTask<Void>() {
                @Override
                public Void onTask() throws Exception {

                    userForRegister.roleId = userDao.getRoleIdFromRoleName(roleName);
                    userForRegister.schoolId = schoolId;
                    userDao.registerUser(userForRegister);
                    int userId = userDao.getMostRecentlyInsertedRow();

                    if (selectedSection.getValue() != null) {
                        return userDao.bindStudentWithSection(new StudentWithSection(userId, selectedSection.getValue().sectionId));
                    } else {
                        throw new Exception("Section Field Missing.");
                    }
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
            uiTask.onFail("Password Mismatch");
        }

    }

    public void register(UITask<Void> uiTask) {

        if (userForRegister.firstName.isEmpty() ||
                userForRegister.middleName.isEmpty() ||
                userForRegister.lastName.isEmpty() ||
                userForRegister.email.isEmpty() ||
                userForRegister.password.isEmpty() ||
                confirmPassword.get().isEmpty() ||
                schoolName.get().isEmpty()) {

            uiTask.onFail("Empty Field");
        } else if (userForRegister.password.equals(confirmPassword.get())) {
            AsyncRunner.runAsync(new QueryTask<Void>() {
                @Override
                public Void onTask() {
                    userForRegister.roleId = userDao.getRoleIdFromRoleName(Roles.role_1);
                    Log.d("RegisterViewModel", userForRegister.roleId + " is your role id.");
                    userForRegister.schoolId = registerSchoolAndGetSchoolId(schoolName.get());
                    return userDao.registerUser(userForRegister);
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
            uiTask.onFail("Password Mismatch");
        }
    }

    public void loadSectionList () {
        AsyncRunner.runAsync(new QueryTask<List<Sections>>() {
            @Override
            public List<Sections> onTask() {
                return userDao.getAllSection();
            }

            @Override
            public void onSuccess(List<Sections> result) {
                sectionsList.postValue(result);
            }

            @Override
            public void onFail(String message) {
            }

            @Override
            public void onUI(List<Sections> result) {
            }
        });
    }
    
    public void registerSection (Sections sections, UITask<Void> uiTask) {
        AsyncRunner.runAsync(new QueryTask<Void>() {
            @Override
            public Void onTask() {
                return userDao.registerSection(sections);
            }

            @Override
            public void onSuccess(Void result) {
                sectionsList.postValue(userDao.getAllSection());
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
