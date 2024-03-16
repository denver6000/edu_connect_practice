package com.denproj.educonnectv2.viewModel;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel
{



    public String name = "Denver";

    @Inject
    public MainViewModel () {

    }
}
