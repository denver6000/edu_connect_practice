package com.denproj.educonnectv2.ui.dashboard.news;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.AddNewsDialogBinding;
import com.denproj.educonnectv2.databinding.FragmentNewsBinding;
import com.denproj.educonnectv2.room.entity.News;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.DashboardViewModel;
import com.denproj.educonnectv2.viewModel.NewsViewModel;

import java.util.List;


public class NewsFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNewsBinding binding = FragmentNewsBinding.inflate(inflater);

        NewsViewModel viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        DashboardViewModel dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.dashboardFragmentContainer);
        binding.addNewsRedirect.setOnClickListener(view -> {
            navController.navigate(R.id.action_newsFragment_to_addNewsFragment);
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        dashboardViewModel.loggedInUser.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                viewModel.getAllNews(user.schoolId, new UITask<List<News>>() {
                    @Override
                    public void onSuccess(List<News> result) {
                        binding.recyclerView.setAdapter(new NewsRecyclerViewAdapter(result));
                    }

                    @Override
                    public void onFail(String message) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return binding.getRoot();
    }
}