package com.example.yumyumplanner.remote.firebase;

public interface LoginFirebase {
    void onLoginSuccess();
    void onLoginFailed(String errorMessage);
}

