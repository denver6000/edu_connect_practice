package com.denproj.educonnectv2.ui.dashboard.group;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentGroupBinding;
import com.denproj.educonnectv2.databinding.GroupCardBinding;
import com.denproj.educonnectv2.room.entity.Group;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.ui.dashboard.group.rcv.GroupRecyclerViewAdapter;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.DashboardViewModel;
import com.denproj.educonnectv2.viewModel.GroupViewModel;

import java.util.List;
import java.util.Objects;

public class GroupFragment extends Fragment {

    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentGroupBinding binding = FragmentGroupBinding.inflate(inflater);
        GroupViewModel viewModel = new ViewModelProvider(requireActivity()).get(GroupViewModel.class);
        DashboardViewModel dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        navController = Navigation.findNavController(requireActivity(), R.id.dashboardFragmentContainer);
        dashboardViewModel.roleName.observe(getViewLifecycleOwner(), s -> {
            if (Objects.equals(dashboardViewModel.roleName.getValue(), Roles.role_3)) {
                binding.addGroupActionButton.setOnClickListener(null);
                binding.addGroupActionButton.setVisibility(View.GONE);
            } else {
                binding.addGroupActionButton.setVisibility(View.VISIBLE);
                binding.addGroupActionButton.setOnClickListener(view -> {
                    navController.navigate(R.id.action_groupFragment_to_addGroupFragment);
                });
            }
        });
        viewModel.loadAllStudents();
        viewModel.loadAllGroup(new UITask<List<Group>>() {
            @Override
            public void onSuccess(List<Group> result) {
                GroupRecyclerViewAdapter adapter = new GroupRecyclerViewAdapter(result);
                binding.groupRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.groupRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}