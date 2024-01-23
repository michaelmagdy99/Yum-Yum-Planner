package com.example.yumyumplanner.authentication.view.signupfragment;

public interface RegisterView {

    void showProgress();
    void hideProgress();
    void showRegisterSuccessMessage();
    void showRegisterErrorMessage(String errorMessage);
}
