package com.example.yumyumplanner.authentication.login.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.yumyumplanner.authentication.login.view.LoginView;
import com.example.yumyumplanner.remote.firebase.authentication.ForgotPasswordCallback;
import com.example.yumyumplanner.remote.firebase.authentication.LoginFirebase;
import com.example.yumyumplanner.model.authentication_repo.AuthenticationRepositryImp;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;

public class LoginPresenterImp implements LoginPresenter, LoginFirebase {
    private  LoginView loginView;
    private AuthenticationRepositryImp authenticationRepositry;
    private static LoginPresenterImp loginPresenterImp;

    public static LoginPresenterImp getInstance(LoginView loginView, Context context) {
        if (loginPresenterImp == null) {
            loginPresenterImp = new LoginPresenterImp(loginView, context);
        }
        return loginPresenterImp;
    }

    private LoginPresenterImp(LoginView loginView, Context context) {
        this.loginView = loginView;
        authenticationRepositry = AuthenticationRepositryImp.getInstance(BackUpDataSourceImp.getInstance(context));
    }

    @Override
    public void login(String email, String pass) {
        loginView.showProgress();
        authenticationRepositry.login(email, pass, this);
    }

    @Override
    public void forgotPassword(String email) {
        if (TextUtils.isEmpty(email)) {
            loginView.showLoginErrorMessage("Email cannot be empty");
            return;
        }

        loginView.showProgress();
        authenticationRepositry.forgotPassword(email, new ForgotPasswordCallback() {
            @Override
            public void onSuccess() {
                loginView.hideProgress();
                loginView.showLoginSuccessMessage();
            }

            @Override
            public void onFailure(String errorMessage) {
                loginView.hideProgress();
                loginView.showLoginErrorMessage(errorMessage);
            }
        });
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
