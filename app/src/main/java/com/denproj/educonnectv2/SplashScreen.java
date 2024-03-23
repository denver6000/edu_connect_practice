package com.denproj.educonnectv2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.denproj.educonnectv2.databinding.ActivityMainBinding;
import com.denproj.educonnectv2.databinding.ActivitySplashScreenBinding;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.ui.dashboard.Dashboard;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.SplashScreenViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding binding;
    Animation in_anim, out_anim, left_anim, right_anim, up_anim, down_anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreenViewModel viewModel = new ViewModelProvider(this).get(SplashScreenViewModel.class);



        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        in_anim = AnimationUtils.loadAnimation(this, R.anim.scale_in_animation);
        out_anim = AnimationUtils.loadAnimation(this, R.anim.scale_out_animation);
        left_anim = AnimationUtils.loadAnimation(this, R.anim.left_animation);
        right_anim = AnimationUtils.loadAnimation(this, R.anim.right_animation);
        up_anim = AnimationUtils.loadAnimation(this, R.anim.up_animation);
        down_anim = AnimationUtils.loadAnimation(this, R.anim.down_animation);

        binding.leftTitle.startAnimation(left_anim);
        binding.rightTitle.startAnimation(right_anim);
        binding.topViews.startAnimation(down_anim);
        binding.bottomViews.startAnimation(up_anim);

        viewModel.checkAndLoadSavedLogin(new UITask<User>() {
            @Override
            public void onSuccess(User result) {


                new Handler().postDelayed(() -> {

                    Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                    intent.putExtra("user", result);
                    startActivity(intent);
                    finish();

                },2000);
            }

            @Override
            public void onFail(String message) {
                Log.d("user", message);
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });















    }
}