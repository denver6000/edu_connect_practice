package com.denproj.educonnectv2.ui.loginRegister;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentRegisterBinding;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RegisterFragment extends Fragment {


    private MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRegisterBinding binding = FragmentRegisterBinding.inflate(inflater);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
        binding.setViewModel(viewModel);
        binding.registerAction.setOnClickListener(view -> viewModel.register(new UITask<Void>() {
            @Override
            public void onSuccess(Void result) {
                Snackbar.make(binding.getRoot(), "Registered! Redirecting to Login", Snackbar.LENGTH_SHORT).show();
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        }));



        return binding.getRoot();
    }
}