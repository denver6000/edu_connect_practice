package com.denproj.educonnectv2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.denproj.educonnectv2.databinding.ActivityMainBinding;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.ui.dashboard.Dashboard;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    Animation in_anim, out_anim;


    MainViewModel viewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        in_anim = AnimationUtils.loadAnimation(this, R.anim.scale_in_animation);
        out_anim = AnimationUtils.loadAnimation(this, R.anim.scale_out_animation);


        binding.linearLayout.startAnimation(in_anim);

        binding.loginAction.setOnClickListener(view -> {

            viewModel.login(new UITask<User>() {
                @Override
                public void onSuccess(User result) {
                    Toast.makeText(MainActivity.this, "Logged In!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                    intent.putExtra("userId", result.userId);
                    startActivity(intent);
                }

                @Override
                public void onFail(String message) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });


        });

        binding.registerBtn.setOnClickListener(view -> inAnimation());

        binding.registerAction.setOnClickListener(view -> {

            binding.linearLayout.clearAnimation();
            binding.linearLayout2.clearAnimation();
            outAnimation();
            viewModel.register(new UITask<Void>() {
                @Override
                public void onSuccess(Void result) {
                    Toast.makeText(MainActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(String message) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });


    }

    public void inAnimation() {
        binding.linearLayout.startAnimation(out_anim);
        binding.linearLayout.setVisibility(View.GONE);


        new Handler().postDelayed(() -> {


            binding.linearLayout2.setVisibility(View.VISIBLE);
            binding.linearLayout2.startAnimation(in_anim);
        },1000);
    }


    public void outAnimation() {
        Toast.makeText(this, "Registration Button Was Clicked", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> {
            binding.linearLayout2.startAnimation(out_anim);
            binding.linearLayout2.setVisibility(View.GONE);

        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.linearLayout.setVisibility(View.VISIBLE);
                binding.linearLayout.startAnimation(in_anim);

            }
        },1000);
    }
}