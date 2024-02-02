package com.example.yumyumplanner.model.authentication_repo;

import android.content.Context;
import android.net.Uri;

import com.example.yumyumplanner.remote.firebase.ForgotPasswordCallback;
import com.example.yumyumplanner.remote.firebase.LoginFirebase;
import com.example.yumyumplanner.remote.firebase.RegisterFirebase;
import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationRepositry {

    void login(String email, String password, LoginFirebase loginFirebase);

    void register(String email, String pass, RegisterFirebase registerFirebase);
    void forgotPassword(String email, ForgotPasswordCallback callback);

}
