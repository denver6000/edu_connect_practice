package com.denproj.educonnectv2.viewModel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.room.entity.Schools;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.QueryTask;
import com.denproj.educonnectv2.util.UITask;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {

    UserDao userDao;

    public MutableLiveData<User> loggedInUser = new MutableLiveData<>(null);



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

    public void registerWithRole(String roleName, String existingSchoolName, UITask<Void> uiTask) {

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
                    userForRegister.schoolId = registerSchoolAndGetSchoolId(existingSchoolName);
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
}
