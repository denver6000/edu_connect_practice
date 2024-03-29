package com.denproj.educonnectv2.ui.dashboard.group;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentAddNewsBinding;
import com.denproj.educonnectv2.databinding.FragmentGroupBinding;

public class GroupFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentGroupBinding binding = FragmentGroupBinding.inflate(inflater);

        return binding.getRoot();
    }
}