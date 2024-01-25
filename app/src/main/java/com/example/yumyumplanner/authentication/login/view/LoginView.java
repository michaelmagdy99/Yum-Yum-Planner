package com.example.yumyumplanner.authentication.login.view;

public interface LoginView {
    void showProgress();
    void hideProgress();
    void showLoginSuccessMessage();
    void showLoginErrorMessage(String errorMessage);
}
