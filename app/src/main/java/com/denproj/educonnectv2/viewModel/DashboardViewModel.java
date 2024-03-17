package com.denproj.educonnectv2.viewModel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.entity.User;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DashboardViewModel extends ViewModel {

    public ObservableField<User> loggedInUser = new ObservableField<>(null);

    @Inject
    public DashboardViewModel () {

    }

}
