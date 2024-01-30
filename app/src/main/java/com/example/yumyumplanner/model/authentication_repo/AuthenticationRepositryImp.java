package com.example.yumyumplanner.model.authentication_repo;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.yumyumplanner.remote.firebase.LoginFirebase;
import com.example.yumyumplanner.remote.firebase.UserFirebaseModel;
import com.example.yumyumplanner.remote.firebase.RegisterFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class AuthenticationRepositryImp implements AuthenticationRepositry{
    private static final String TAG = "AuthenticationRepositry";
    private static AuthenticationRepositryImp authenticationRepositryImp;
    private UserFirebaseModel userFirebaseModel;
    public static synchronized AuthenticationRepositryImp getInstance() {
        if (authenticationRepositryImp == null) {
            authenticationRepositryImp = new AuthenticationRepositryImp();
        }
        return authenticationRepositryImp;
    }

    private AuthenticationRepositryImp() {
        this.userFirebaseModel = UserFirebaseModel.getInstance();
    }
    @Override
    public void login(String email, String password, LoginFirebase loginFirebase) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            loginFirebase.onLoginFailed("Please enter email and Password!!");
        } else {

            userFirebaseModel.getAuth().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            loginFirebase.onLoginSuccess();
                        } else {
                            loginFirebase.onLoginFailed("Login failed!!");
                        }
                    });
        }
    }

    @Override
    public void register(String email, String password, RegisterFirebase registerFirebase) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            registerFirebase.onRegisterFailed("Please enter email and Password!!");

        } else {

            userFirebaseModel.getAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                registerFirebase.onRegisterSuccess();
                            }
                            else {
                                registerFirebase.onRegisterFailed("Sign up is failed, try again");
                            }
                        }
                    });
        }

    }

}
