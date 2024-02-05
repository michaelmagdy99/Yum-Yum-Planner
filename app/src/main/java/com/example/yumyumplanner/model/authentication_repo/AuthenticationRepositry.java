package com.example.yumyumplanner.model.authentication_repo;

import com.example.yumyumplanner.remote.firebase.authentication.ForgotPasswordCallback;
import com.example.yumyumplanner.remote.firebase.authentication.LoginFirebase;
import com.example.yumyumplanner.remote.firebase.authentication.RegisterFirebase;

public interface AuthenticationRepositry {

    void login(String email, String password, LoginFirebase loginFirebase);

    void register(String email, String password, String name, RegisterFirebase registerFirebase);
    void forgotPassword(String email, ForgotPasswordCallback callback);

}
