package com.denproj.educonnectv2.ui.dashboard.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denproj.educonnectv2.MainActivity;
import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentProfileBinding;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.DashboardViewModel;


public class ProfileFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater);

        DashboardViewModel viewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        binding.floatingLogOut.setOnClickListener(view -> viewModel.logOut(new UITask<Void>() {
            @Override
            public void onSuccess(Void result) {
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }

            @Override
            public void onFail(String message) {

            }
        }));

        return binding.getRoot();
    }
}