package com.denproj.educonnectv2.ui.loginRegister;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentLoginBinding;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLoginBinding binding = FragmentLoginBinding.inflate(inflater);
        MainViewModel viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
        binding.setViewModel(viewModel);

        binding.loginAction.setOnClickListener(view -> viewModel.login(new UITask<User>() {
            @Override
            public void onSuccess(User result) {
                Toast.makeText(requireContext(), "Welcome " + result.firstName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        }));

        binding.registerBtn.setOnClickListener(view -> {
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        });
        return binding.getRoot();
    }
}