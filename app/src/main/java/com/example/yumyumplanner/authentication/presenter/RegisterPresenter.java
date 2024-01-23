package com.example.yumyumplanner.authentication.presenter;

import com.example.yumyumplanner.authentication.view.loginfragment.LoginView;
import com.example.yumyumplanner.authentication.view.signupfragment.RegisterView;
import com.example.yumyumplanner.firebase.login.LoginFirebaseModel;
import com.example.yumyumplanner.firebase.register.RegisterFirebase;
import com.example.yumyumplanner.firebase.register.RegisterFirebaseModel;

public class RegisterPresenter {

    private RegisterView registerView;
    private RegisterFirebaseModel registerFirebaseModel;

    public RegisterPresenter(RegisterView registerView, RegisterFirebaseModel registerFirebaseModel) {
        this.registerView = registerView;
        this.registerFirebaseModel = registerFirebaseModel;
    }

    public void registerUser(String email, String password){
        registerView.showProgress();

        registerFirebaseModel.registerUser(email, password, new RegisterFirebase() {
            @Override
            public void onRegisterSuccess() {
                registerView.hideProgress();
                registerView.showRegisterSuccessMessage();
            }

            @Override
            public void onRegisterFailed(String errorMessage) {
                registerView.hideProgress();
                registerView.showRegisterErrorMessage(errorMessage);
            }
        });
    }
}
