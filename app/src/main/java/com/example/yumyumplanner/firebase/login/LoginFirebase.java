package com.example.yumyumplanner.firebase.login;

public interface LoginFirebase {
    void onLoginSuccess();
    void onLoginFailed(String errorMessage);
}

