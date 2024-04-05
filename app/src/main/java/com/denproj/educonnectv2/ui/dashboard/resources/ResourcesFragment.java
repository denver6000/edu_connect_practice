package com.denproj.educonnectv2.ui.dashboard.resources;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentResourcesBinding;
import com.denproj.educonnectv2.room.entity.Resource;
import com.denproj.educonnectv2.ui.dashboard.resources.rcv.ResourceRCV;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.ResourceViewModel;

import java.util.List;

public class ResourcesFragment extends Fragment {

    public static String RESOURCE_FOLDER = "resources";
    FragmentResourcesBinding binding;
    ResourceRCV adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResourcesBinding.inflate(inflater);
        ResourceViewModel viewModel = new ViewModelProvider(requireActivity()).get(ResourceViewModel.class);
        binding.resources.setLayoutManager(new LinearLayoutManager(requireContext()));
        viewModel.getAllResource(new UITask<List<Resource>>() {
            @Override
            public void onSuccess(List<Resource> result) {
                adapter = new ResourceRCV(result, viewModel.userDao);
                binding.resources.setAdapter(adapter);
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        NavController navController = Navigation.findNavController(requireActivity(), R.id.dashboardFragmentContainer);
        binding.addResourceRedirect.setOnClickListener(view -> {
            navController.navigate(R.id.action_resourcesFragment_to_addResourceFragment);
        });

        return binding.getRoot();
    }
}