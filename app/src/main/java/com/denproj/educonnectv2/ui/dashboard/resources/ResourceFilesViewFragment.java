package com.denproj.educonnectv2.ui.dashboard.resources;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentResourceFilesViewBinding;

public class ResourceFilesViewFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentResourceFilesViewBinding binding = FragmentResourceFilesViewBinding.inflate(inflater);
        return binding.getRoot();
    }
}