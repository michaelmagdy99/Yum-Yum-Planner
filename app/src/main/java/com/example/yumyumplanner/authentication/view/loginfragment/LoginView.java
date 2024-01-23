package com.example.yumyumplanner.authentication.view.loginfragment;

public interface LoginView {
    void showProgress();
    void hideProgress();
    void showLoginSuccessMessage();
    void showLoginErrorMessage(String errorMessage);
}
