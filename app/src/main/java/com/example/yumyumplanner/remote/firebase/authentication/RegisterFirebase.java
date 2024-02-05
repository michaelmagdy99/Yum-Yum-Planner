package com.example.yumyumplanner.remote.firebase.authentication;

public interface RegisterFirebase {
    void onRegisterSuccess();
    void onRegisterFailed(String errorMessage);
}
