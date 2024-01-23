package com.example.yumyumplanner.firebase.login;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;

public class LoginFirebaseModel {
    public void loginUser(String email, String password, LoginFirebase listener) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            listener.onLoginFailed("Please enter email and Password!!");

        } else {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            listener.onLoginSuccess();
                        } else {
                            listener.onLoginFailed("Login failed!!");
                        }
                    });
        }
    }

}
