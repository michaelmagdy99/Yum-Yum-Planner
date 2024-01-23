package com.example.yumyumplanner.authentication.presenter;

import com.example.yumyumplanner.authentication.view.loginfragment.LoginView;
import com.example.yumyumplanner.firebase.login.LoginFirebase;
import com.example.yumyumplanner.firebase.login.LoginFirebaseModel;

public class LoginPresenter {
    private  LoginView loginView;
    private LoginFirebaseModel loginFirebase;

    public LoginPresenter(LoginView loginView, LoginFirebaseModel loginFirebase) {
        this.loginView = loginView;
        this.loginFirebase = loginFirebase;
    }
    public void loginUser(String email, String password) {
        loginView.showProgress();

        loginFirebase.loginUser(email, password, new LoginFirebase() {
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
        });
    }

}
