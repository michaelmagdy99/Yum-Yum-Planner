package com.example.yumyumplanner.authentication.login.presenter;

public interface LoginPresenter {
    void login(String email , String pass);

    void forgotPassword(String email);
}
