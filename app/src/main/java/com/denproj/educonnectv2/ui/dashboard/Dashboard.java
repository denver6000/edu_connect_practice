package com.denproj.educonnectv2.ui.dashboard;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavArgs;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.ActivityDashboardBinding;
import com.denproj.educonnectv2.databinding.SidebarHeaderDashboardBinding;
import com.denproj.educonnectv2.room.entity.Roles;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.DashboardViewModel;
import com.denproj.educonnectv2.viewModel.NewsViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

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
        User user = getIntent().getParcelableExtra("user");
        if (user != null) {
            Toast.makeText(this, "User is Logged in", Toast.LENGTH_SHORT).show();
            viewModel.loggedInUser.setValue(user);
            viewModel.loadUserSchool(user);
            viewModel.loadRoleName(user, new UITask<String>() {
                @Override
                public void onSuccess(String result) {

                    SidebarHeaderDashboardBinding headerDashboardBinding = SidebarHeaderDashboardBinding.bind(navigationView.getHeaderView(0));
                    headerDashboardBinding.email.setText(user.email);
                    String name = user.firstName + " " + user.lastName;
                    headerDashboardBinding.name.setText(name);

                    if (result.equals(Roles.role_1)) {

                        navigationView.inflateMenu(R.menu.dashboard_admin_menu);
                        appBarConfiguration = new AppBarConfiguration
                                .Builder(R.id.newsFragment, R.id.registerFragmentTeacher, R.id.calendaryFragment, R.id.groupFragment, R.id.resourcesFragment, R.id.profileFragment)
                                .setOpenableLayout(drawerLayout)
                                .build();
                    } else if (result.equals(Roles.role_2)) {
                        navigationView.inflateMenu(R.menu.dashboard_teacher_menu);
                        appBarConfiguration = new AppBarConfiguration
                                .Builder(R.id.newsFragment, R.id.registerFragmentTeacher, R.id.calendaryFragment, R.id.groupFragment, R.id.resourcesFragment, R.id.profileFragment)
                                .setOpenableLayout(drawerLayout)
                                .build();
                    } else {
                        navigationView.inflateMenu(R.menu.dashboard_menu);
                        appBarConfiguration = new AppBarConfiguration
                                .Builder(R.id.newsFragment, R.id.calendaryFragment, R.id.groupFragment, R.id.resourcesFragment)
                                .setOpenableLayout(drawerLayout)
                                .build();
                    }


                }

                @Override
                public void onFail(String message) {

                }
            });
        }






        NavController navController =  ((NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.dashboardFragmentContainer)).getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.dashboardFragmentContainer);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}