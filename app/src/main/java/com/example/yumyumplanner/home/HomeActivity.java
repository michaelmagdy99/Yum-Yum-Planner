package com.example.yumyumplanner.home;

import static com.example.yumyumplanner.utils.InternetConnectivity.isConnectedToInternet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.airbnb.lottie.LottieAnimationView;
import com.example.yumyumplanner.authentication.AuthenticationActivity;
import com.example.yumyumplanner.database.MealDatabase;
import com.example.yumyumplanner.database.MealsLocalDataSourceImp;
import com.example.yumyumplanner.model.data.UserProfile;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;
import com.example.yumyumplanner.remote.firebase.backup.UserDataCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.yumyumplanner.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {
    public static boolean isGuestMode = false;
    private View rootView;

    BackUpDataSourceImp backUpDataSourceImp;

    static LottieAnimationView loadingAnmie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadingAnmie = findViewById(R.id.animation_view_home);

        rootView = findViewById(android.R.id.content);

        backUpDataSourceImp = BackUpDataSourceImp.getInstance(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavController navController = Navigation.findNavController(this, R.id.home_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int destinationId = destination.getId();
            if (destinationId == R.id.home ||
                    destinationId == R.id.search ||
                    destinationId == R.id.favourite ||
                    destinationId == R.id.profile ||
                    destinationId == R.id.calendar) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else {
                bottomNavigationView.setVisibility(View.GONE);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (isGuestMode) {
                int itemId = item.getItemId();
                if (itemId == R.id.profile || itemId == R.id.calendar || itemId == R.id.favourite) {
                    showGuestModeMessage();
                    return false;
                }
            }

            return NavigationUI.onNavDestinationSelected(item, navController);
        });

        if (!isConnectedToInternet(this)) {
            showNoInternetSnackbar();
        }

        if(UserProfile.getCurrentUserId() != null){
           // MealsLocalDataSourceImp.getInstance(this).insertMeal(backUpDataSourceImp.setBackUpFavInRoom().get(0));
            Log.i("TAG", "onCreate: ======================");
            backUpDataSourceImp.setBackUpFavInRoom(this);
            backUpDataSourceImp.setBackUpCalInRoom(this);
            backUpDataSourceImp.getUserData(new UserDataCallback() {
                @Override
                public void onSuccess(UserProfile userProfile) {

                    String name = userProfile.getName();
                    String imageURL = userProfile.getProfileImageURL();
                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UserName", name);
                    editor.putString("UserProfileImageURL", imageURL);
                    editor.apply();

                    Log.i("TAG", "onSuccess: in Activity" + userProfile.getProfileImageURL() + userProfile.getName());
                }

                @Override
                public void onFailure(String error) {
                    Log.i("TAG", "onFailure: in Activity");
                }
            });
        }else{
            showGuestModeMessage();
        }

    }
    public void showGuestModeMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign Up For More Features");
        builder.setMessage("Add your food preferences, shop your recipes, plan your meals and more!");

        builder.setPositiveButton("SIGN UP", (dialog, which) -> {
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
        });

        builder.setNegativeButton("CANCEL", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showNoInternetSnackbar() {
        Snackbar snackbar = Snackbar.make(rootView, "No internet connection", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", v -> snackbar.dismiss());
        snackbar.show();
    }

    public static void showLoadingAnimation() {
        if (loadingAnmie != null) {
            loadingAnmie.setVisibility(View.VISIBLE);
        }
    }

    public static void hideLoadingAnimation() {
        if (loadingAnmie != null) {
            loadingAnmie.setVisibility(View.GONE);
        }
    }


}
