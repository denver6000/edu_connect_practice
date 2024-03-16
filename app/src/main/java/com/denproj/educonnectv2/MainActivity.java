package com.denproj.educonnectv2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.denproj.educonnectv2.databinding.ActivityMainBinding;
import com.denproj.educonnectv2.ui.dashboard.Dashboard;
import com.denproj.educonnectv2.viewModel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    Animation in_anim, out_anim, right_anim, left_anim, up_anim, down_anim;

    MainViewModel viewModel;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        in_anim = AnimationUtils.loadAnimation(this, R.anim.scale_in_animation);
        out_anim = AnimationUtils.loadAnimation(this, R.anim.scale_out_animation);
        right_anim = AnimationUtils.loadAnimation(this, R.anim.right_animation);
        left_anim = AnimationUtils.loadAnimation(this, R.anim.left_animation);
        up_anim = AnimationUtils.loadAnimation(this, R.anim.up_animation);
        down_anim = AnimationUtils.loadAnimation(this, R.anim.down_animation);


        binding.linearLayout.startAnimation(left_anim);

        binding.loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);


            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.linearLayout.startAnimation(out_anim);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.linearLayout.setVisibility(View.GONE);
                        binding.linearLayout2.setVisibility(View.VISIBLE);
                        binding.linearLayout2.startAnimation(in_anim);
                    }
                },1000);

            }
        });


    }
}