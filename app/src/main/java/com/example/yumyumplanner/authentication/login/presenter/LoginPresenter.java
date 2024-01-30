package com.example.yumyumplanner.authentication.login.presenter;

import android.app.Activity;

public interface LoginPresenter {
    void login(String email , String pass);

    void forgotPassword(String email);
}
