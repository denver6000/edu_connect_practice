package com.denproj.educonnectv2.ui.dashboard.news;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentAddNewsBinding;
import com.denproj.educonnectv2.viewModel.NewsViewModel;

public class AddNewsFragment extends Fragment {

    ActivityResultLauncher<String> launcher;

    Animation in_anim, out_anim;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAddNewsBinding binding = FragmentAddNewsBinding.inflate(inflater);
        NewsViewModel viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        binding.setViewModel(viewModel);

        in_anim = AnimationUtils.loadAnimation(getContext(), R.anim.scale_in_animation);
        out_anim = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out_animation);

        binding.linearLayout.startAnimation(in_anim);



        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
//                Log.d("AddNewsFragment", result.get);
            }
        });

        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });

        return binding.getRoot();
    }


}