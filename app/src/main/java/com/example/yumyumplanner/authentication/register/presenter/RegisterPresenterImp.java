package com.example.yumyumplanner.authentication.register.presenter;

import android.content.Context;

import com.example.yumyumplanner.authentication.register.view.RegisterView;
import com.example.yumyumplanner.remote.firebase.authentication.RegisterFirebase;
import com.example.yumyumplanner.model.authentication_repo.AuthenticationRepositry;
import com.example.yumyumplanner.model.authentication_repo.AuthenticationRepositryImp;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;

public class RegisterPresenterImp implements RegisterPresetner, RegisterFirebase {

    private RegisterView registerView;
    private AuthenticationRepositry authenticationRepositry;
    private static RegisterPresenterImp registerPresenter;

    public static RegisterPresenterImp getInstance(RegisterView registerView, Context context) {
        if (registerPresenter == null) {
            registerPresenter = new RegisterPresenterImp(registerView, context);
        }
        return registerPresenter;
    }
    private RegisterPresenterImp(RegisterView registerView, Context context) {
        this.registerView = registerView;
        authenticationRepositry = AuthenticationRepositryImp.getInstance(BackUpDataSourceImp.getInstance(context));
    }


    @Override
    public void register(String name, String email, String pass) {
        registerView.showProgress();
        authenticationRepositry.register(name,email, pass, this);
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
