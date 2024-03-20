package com.denproj.educonnectv2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.denproj.educonnectv2.databinding.ActivityMainBinding;
import com.denproj.educonnectv2.ui.dashboard.Dashboard;
import com.denproj.educonnectv2.viewModel.MainViewModel;

import java.security.Permission;
import java.security.Permissions;
import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    Animation in_anim, out_anim, right_anim, left_anim, up_anim, down_anim;

    MainViewModel viewModel;


    ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            if (o) {
                Toast.makeText(MainActivity.this, "You may not be able to upload pictures.", Toast.LENGTH_SHORT).show();
            }
        }
    });
    

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


        viewModel.attemptToRegisterRoles(this);

        if (checkSelfPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Alert")
                    .setMessage("Please allow permissions to get full experience")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            requestPermissionLauncher.launch(Manifest.permission.MANAGE_EXTERNAL_STORAGE);
                        }
                    })
                    .setNegativeButton("No", (dialogInterface, i) -> {
                        Toast.makeText(this, "You will not be able to upload images and files.", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        }
    }
}