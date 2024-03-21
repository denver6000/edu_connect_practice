package com.denproj.educonnectv2.ui.loginRegister;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentRegisterBinding;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.DashboardViewModel;
import com.denproj.educonnectv2.viewModel.MainViewModel;
import com.denproj.educonnectv2.viewModel.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class RegisterFragment extends Fragment implements UITask<Void>{


    private RegisterViewModel viewModel;
    private NavController navController;
    FragmentRegisterBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);

        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
        DashboardViewModel dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        try {
            navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
        } catch (IllegalArgumentException e) {
            navController = Navigation.findNavController(requireActivity(), R.id.dashboardFragmentContainer);
        }

        binding.setViewModel(viewModel);
        if (Objects.equals(dashboardViewModel.roleName.getValue(), Roles.role_1)) {
            binding.schoolField.getEditText().setText(dashboardViewModel.schoolName.getValue());
            binding.schoolField.getEditText().setEnabled(false);
            binding.registerAction.setOnClickListener(view -> viewModel.registerWithRole(Roles.role_2, dashboardViewModel.schoolName.getValue(), new UITask<Void>() {
                @Override
                public void onSuccess(Void result) {
                    Toast.makeText(requireContext(), "Added Teacher", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(String message) {

                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            }));
        } else {
            binding.registerAction.setOnClickListener(view -> viewModel.register(this));
        }

        return binding.getRoot();
    }

    @Override
    public void onSuccess(Void result) {
        Snackbar.make(binding.getRoot(), "Registered! Redirecting to Login", Snackbar.LENGTH_SHORT).show();
        navController.navigate(R.id.action_registerFragment_to_loginFragment);
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}