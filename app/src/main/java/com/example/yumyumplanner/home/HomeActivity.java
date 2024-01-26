package com.example.yumyumplanner.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.home.calendar.CalendarFragment;
import com.example.yumyumplanner.home.favourite.FavouriteFragment;
import com.example.yumyumplanner.home.home.view.HomeFragment;
import com.example.yumyumplanner.home.profile.ProfileFragment;
import com.example.yumyumplanner.home.search.SearchFragment;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavController navController = Navigation.findNavController(this, R.id.home_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.mealDetailsFragment) {
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

        if (savedInstanceState == null) {
            navController.navigate(R.id.homeFragment);
        }


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                navController.navigate(R.id.homeFragment);
                return true;
            } else if (itemId == R.id.search) {
                navController.navigate(R.id.searchFragment);
                return true;
            } else if (itemId == R.id.calendar) {
                navController.navigate(R.id.calendarFragment);
                return true;
            } else if (itemId == R.id.favourite) {
                navController.navigate(R.id.favouriteFragment);
                return true;
            } else {
                return false;
            }

        });


    }

}
