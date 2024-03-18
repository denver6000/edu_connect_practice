package com.denproj.educonnectv2.ui.dashboard;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.ActivityDashboardBinding;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.DashboardViewModel;
import com.google.android.material.navigation.NavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Dashboard extends AppCompatActivity {

    ActivityDashboardBinding binding;
    DashboardViewModel viewModel;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);


        NavigationView navigationView = binding.dashboardNavigationView;
        DrawerLayout drawerLayout = binding.drawerLayout;
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.newsFragment, R.id.calendaryFragment, R.id.groupFragment, R.id.resourcesFragment).setOpenableLayout(drawerLayout).build();

        NavController navController =  ((NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.dashboardFragmentContainer)).getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        int userId = DashboardArgs.fromBundle(savedInstanceState).getUserId();
//        if (userId != 0) {
//            viewModel.getUser(userId, new UITask<User>() {
//                @Override
//                public void onSuccess(User result) {
//                    Toast.makeText(Dashboard.this, "Welcome ", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFail(String message) {
//
//                }
//            });
//        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.dashboardFragmentContainer);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}