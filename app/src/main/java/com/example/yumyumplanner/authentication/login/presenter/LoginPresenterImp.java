package com.example.yumyumplanner.authentication.login.presenter;

import com.example.yumyumplanner.authentication.login.view.LoginView;
import com.example.yumyumplanner.remote.firebase.LoginFirebase;
import com.example.yumyumplanner.model.authentication_repo.AuthenticationRepositry;
import com.example.yumyumplanner.model.authentication_repo.AuthenticationRepositryImp;

public class LoginPresenterImp implements LoginPresenter, LoginFirebase{
    private  LoginView loginView;
    private AuthenticationRepositry authenticationRepositry;
    private static LoginPresenterImp loginPresenterImp;

    public static LoginPresenterImp getInstance(LoginView loginView) {
        if (loginPresenterImp == null) {
            loginPresenterImp = new LoginPresenterImp(loginView);
        }
        return loginPresenterImp;
    }

    private LoginPresenterImp(LoginView loginView) {
        this.loginView = loginView;
        authenticationRepositry = AuthenticationRepositryImp.getInstance();
    }

    @Override
    public void login(String email, String pass) {
        loginView.showProgress();
        authenticationRepositry.login(email, pass, this);
    }

    @Override
    public void forgotPassword(String email) {

    }

    @Override
    public void onLoginSuccess() {
        loginView.hideProgress();
        loginView.showLoginSuccessMessage();
    }

    @Override
    public void onLoginFailed(String errorMessage) {
        loginView.hideProgress();
        loginView.showLoginErrorMessage(errorMessage);
    }
}
