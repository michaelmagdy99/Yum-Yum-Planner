package com.example.yumyumplanner.authentication.register.presenter;

import com.example.yumyumplanner.authentication.register.view.RegisterView;
import com.example.yumyumplanner.firebase.RegisterFirebase;
import com.example.yumyumplanner.model.authentication_repo.AuthenticationRepositry;
import com.example.yumyumplanner.model.authentication_repo.AuthenticationRepositryImp;

public class RegisterPresenterImp implements RegisterPresetner, RegisterFirebase {

    private RegisterView registerView;
    private AuthenticationRepositry authenticationRepositry;
    private static RegisterPresenterImp registerPresenter;

    public static RegisterPresenterImp getInstance(RegisterView registerView) {
        if (registerPresenter == null) {
            registerPresenter = new RegisterPresenterImp(registerView);
        }
        return registerPresenter;
    }
    private RegisterPresenterImp(RegisterView registerView) {
        this.registerView = registerView;
        authenticationRepositry = AuthenticationRepositryImp.getInstance();
    }


    @Override
    public void register(String email, String pass) {
        registerView.showProgress();
        authenticationRepositry.register(email, pass, this);
    }

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
}
