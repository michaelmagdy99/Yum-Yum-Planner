package com.example.yumyumplanner.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.database.MealDAO;
import com.example.yumyumplanner.database.MealDatabase;
import com.example.yumyumplanner.home.HomeActivity;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authtication);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    }
}