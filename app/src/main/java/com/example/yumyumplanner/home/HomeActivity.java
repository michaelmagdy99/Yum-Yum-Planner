package com.example.yumyumplanner.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.os.Bundle;
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

        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    replaceFragment(new HomeFragment());
                } else if (itemId == R.id.search) {
                    replaceFragment(new SearchFragment());
                } else if (itemId == R.id.profile) {
                    replaceFragment(new ProfileFragment());
                } else if (itemId == R.id.calendar) {
                    replaceFragment(new CalendarFragment());
                } else if (itemId == R.id.favourite) {
                    replaceFragment(new FavouriteFragment());
                }
                return true;
            }
        });


    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
