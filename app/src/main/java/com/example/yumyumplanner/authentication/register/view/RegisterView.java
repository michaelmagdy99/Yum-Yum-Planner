package com.example.yumyumplanner.authentication.register.view;

public interface RegisterView {

    void showProgress();
    void hideProgress();
    void showRegisterSuccessMessage();
    void showRegisterErrorMessage(String errorMessage);
}
