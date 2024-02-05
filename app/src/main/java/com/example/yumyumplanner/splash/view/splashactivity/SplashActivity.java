package com.example.yumyumplanner.splash.view.splashactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.authentication.AuthenticationActivity;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 3000;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_ONBOARDING_SHOWN = "onboardingShown";

    private FragmentManager fragmentManager;

    private TextView skipBtn;
    private TextView getStartedBtn;
    private TextView exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        skipBtn = findViewById(R.id.skip_btn);
        getStartedBtn = findViewById(R.id.get_started_btn);
        exitBtn = findViewById(R.id.exit_btn);

        skipBtn.setVisibility(View.INVISIBLE);
        getStartedBtn.setVisibility(View.INVISIBLE);
        exitBtn.setVisibility(View.INVISIBLE);

        setSplashScreenTimeOut();

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAuthenticationActivity();
            }
        });
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAuthenticationActivity();
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void goToAuthenticationActivity() {
        Intent intent = new Intent(SplashActivity.this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }

    private void setSplashScreenTimeOut() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                boolean onboardingShown = sharedPreferences.getBoolean(PREF_ONBOARDING_SHOWN, false);

                if (onboardingShown) {
                    goToAuthenticationActivity();
                } else {
                    skipBtn.setVisibility(View.VISIBLE);
                    replaceFragment();
                }
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

    private void replaceFragment() {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_ONBOARDING_SHOWN, true);
        editor.apply();

        fragmentManager = getSupportFragmentManager();
        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataforOnboarding());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.splash_fragment, paperOnboardingFragment);
        fragmentTransaction.commit();

        paperOnboardingFragment.setOnChangeListener(new PaperOnboardingOnChangeListener() {
            @Override
            public void onPageChanged(int oldIndex, int newIndex) {
                if (newIndex == getDataforOnboarding().size() - 1) {
                    exitBtn.setVisibility(View.VISIBLE);
                    getStartedBtn.setVisibility(View.VISIBLE);
                } else {
                    exitBtn.setVisibility(View.INVISIBLE);
                    getStartedBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    // Onboarding Fragment
    private ArrayList<PaperOnboardingPage> getDataforOnboarding() {
        PaperOnboardingPage source = new PaperOnboardingPage(
                "Personalized Recipe Discovery\n",
                "Tell us your food preference and we'll serve you delicious",
                Color.parseColor("#F3F3F3"),
                R.drawable.onboarding2,
                R.drawable.onboarding2);

        PaperOnboardingPage source1 = new PaperOnboardingPage(
                "Grocery List for when you shop\n",
                "Take the grocery list from your cart to your nearest grocery store and buy the ingredient",
                Color.parseColor("#F3F3F3"),
                R.drawable.onboarding3,
                R.drawable.onboarding3);

        PaperOnboardingPage source2 = new PaperOnboardingPage(
                "All your favourite Recipes in one place\n",
                "Save time planning by having your favourite recipes within reach",
                Color.parseColor("#F3F3F3"),
                R.drawable.onboarding1,
                R.drawable.onboarding1);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(source);
        elements.add(source1);
        elements.add(source2);
        return elements;
    }



}
