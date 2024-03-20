package com.denproj.educonnectv2.ui.dashboard.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentAddNewsBinding;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.DashboardViewModel;
import com.denproj.educonnectv2.viewModel.MainViewModel;
import com.denproj.educonnectv2.viewModel.NewsViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class AddNewsFragment extends Fragment {

    ActivityResultLauncher<PickVisualMediaRequest> launcher;

    Animation in_anim, out_anim;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAddNewsBinding binding = FragmentAddNewsBinding.inflate(inflater);
        NewsViewModel viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        DashboardViewModel dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        binding.setViewModel(viewModel);

        in_anim = AnimationUtils.loadAnimation(getContext(), R.anim.scale_in_animation);
        out_anim = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out_animation);

        binding.linearLayout.startAnimation(in_anim);



        launcher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                Toast.makeText(requireContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                binding.imageView2.setImageURI(uri);
                Bitmap bitmap;
                try (InputStream inputStream = getContext().getContentResolver().openInputStream(uri)) {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    viewModel.news.imageByteArray = stream.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
            }
        });

        binding.button.setOnClickListener(view -> {
            viewModel.news.schoolScope = dashboardViewModel.loggedInUser.getValue().schoolId;
            viewModel.saveNews(new UITask<Void>() {
                @Override
                public void onSuccess(Void result) {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.dashboardFragmentContainer);
                    navController.navigate(AddNewsFragmentDirections.actionAddNewsFragmentToNewsFragment());
                }

                @Override
                public void onFail(String message) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        return binding.getRoot();
    }


}