package com.denproj.educonnectv2.ui.loginRegister;

import android.app.AlertDialog;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.AddSectionFragmentBinding;
import com.denproj.educonnectv2.databinding.FragmentRegisterBinding;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.room.entity.Sections;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.DashboardViewModel;
import com.denproj.educonnectv2.viewModel.MainViewModel;
import com.denproj.educonnectv2.viewModel.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ActivityComponent;

public class RegisterFragment extends Fragment implements UITask<Void>, AdapterView.OnItemSelectedListener {


    private RegisterViewModel viewModel;
    private NavController navController;
    FragmentRegisterBinding binding;
    DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater);
        this.setUp();

        dashboardViewModel.loggedInUser.observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                setVisibilityOfSectionField(View.GONE);
                binding.registerAction.setOnClickListener(view -> viewModel.register(this));
                Toast.makeText(requireContext(), "You are registering as an admin.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Objects.equals(dashboardViewModel.roleName.getValue(), Roles.role_1)) {

                freezeSchoolField();
                setVisibilityOfSectionField(View.GONE);
                setCallbackForTeacherRegister(user);

            } else if (Objects.equals(dashboardViewModel.roleName.getValue(), Roles.role_2)) {

                freezeSchoolField();
                setVisibilityOfSectionField(View.VISIBLE);
                viewModel.loadSectionList();
                viewModel.selectedSectionIndex.setValue(binding.sectionSpinner.getSelectedItemPosition());
                setCallbackForStudentRegister();
                binding.sectionSpinner.setOnItemSelectedListener(this);

                viewModel.sectionsList.observe(getViewLifecycleOwner(), sections -> {
                    if (sections != null) {
                        ArrayAdapter<Sections> sectionsArrayAdapter = new ArrayAdapter<>(requireActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sections);
                        binding.sectionSpinner.setAdapter(sectionsArrayAdapter);
                    }
                });

                binding.addSectionButton.setOnClickListener(view -> {
                    showAddSectionDialog();
                });

                viewModel.selectedSectionIndex.observe(getViewLifecycleOwner(), integer -> {
                    if (viewModel.sectionsList.getValue() != null) {
                        viewModel.selectedSection.setValue(viewModel.sectionsList.getValue().get(integer));
                    }
                });

            }
        });

        return binding.getRoot();
    }

    void setCallbackForTeacherRegister(User user) {
        binding.registerAction.setOnClickListener(view -> viewModel.registerWithRole(Roles.role_2, user.schoolId, new UITask<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(requireContext(), "Added Teacher", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {

                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    void setCallbackForStudentRegister() {
        binding.registerAction.setOnClickListener(view -> viewModel.registerWithStudent(Roles.role_3, Objects.requireNonNull(dashboardViewModel.loggedInUser.getValue()).schoolId, new UITask<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                Log.e("Error", message);
            }
        }));
    }

    void setUp() {

        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        try {
            navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
        } catch (IllegalArgumentException e) {
            navController = Navigation.findNavController(requireActivity(), R.id.dashboardFragmentContainer);
        }

        binding.setViewModel(viewModel);
    }

    void showAddSectionDialog() {

        AddSectionFragmentBinding dialogBinding = AddSectionFragmentBinding.inflate(getLayoutInflater());
        dialogBinding.setSection(new Sections());

        dialogBinding.registerSection.setOnClickListener(view -> {
            viewModel.registerSection(dialogBinding.getSection(), new UITask<Void>() {
                @Override
                public void onSuccess(Void result) {
                    Toast.makeText(requireContext(), "Section Registered", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(String message) {

                }
            });
        });

        new AlertDialog.Builder(requireContext())
                .setView(dialogBinding.getRoot())
                .show();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        viewModel.selectedSectionIndex.setValue(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        Toast.makeText(requireContext(), "Nothing is selected.", Toast.LENGTH_SHORT).show();
    }

    void freezeSchoolField() {
        viewModel.schoolName.set(dashboardViewModel.schoolName.getValue());
        binding.schoolField.getEditText().setEnabled(false);
    }

    void setVisibilityOfSectionField(int visibility) {
        binding.sectionSpinner.setVisibility(visibility);
        binding.sectionsFieldWrapper.setVisibility(visibility);
    }
}