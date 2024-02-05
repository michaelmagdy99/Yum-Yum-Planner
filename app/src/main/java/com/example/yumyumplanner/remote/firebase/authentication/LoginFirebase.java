package com.example.yumyumplanner.remote.firebase.authentication;

public interface LoginFirebase {
    void onLoginSuccess();
    void onLoginFailed(String errorMessage);
}

