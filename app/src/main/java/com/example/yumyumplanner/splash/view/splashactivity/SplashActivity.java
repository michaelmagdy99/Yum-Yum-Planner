package com.example.yumyumplanner.splash.view.splashactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.authentication.view.authenticationactivity.AuthenticationActivity;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 3000;

    private FragmentManager fragmentManager;

    private TextView skipBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        skipBtn = findViewById(R.id.skip_btn);
        skipBtn.setVisibility(View.INVISIBLE);

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this , AuthenticationActivity.class);
                startActivity(intent);
            }
        });

        setSplashScreenTimeOut();
    }

    private void setSplashScreenTimeOut(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                skipBtn.setVisibility(View.VISIBLE);
                fragmentManager = getSupportFragmentManager();
                final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataforOnboarding());
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.splash_fragment, paperOnboardingFragment);
                fragmentTransaction.commit();

            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

    //onBoarding Fragment
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