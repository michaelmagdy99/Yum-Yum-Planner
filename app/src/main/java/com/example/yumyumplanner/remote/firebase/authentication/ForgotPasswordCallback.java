package com.example.yumyumplanner.remote.firebase.authentication;

public interface ForgotPasswordCallback {
    void onSuccess();
    void onFailure(String error);
}
