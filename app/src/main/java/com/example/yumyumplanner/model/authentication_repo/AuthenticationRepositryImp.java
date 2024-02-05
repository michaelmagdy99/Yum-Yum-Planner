package com.example.yumyumplanner.model.authentication_repo;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.yumyumplanner.model.data.UserProfile;
import com.example.yumyumplanner.remote.firebase.authentication.ForgotPasswordCallback;
import com.example.yumyumplanner.remote.firebase.authentication.LoginFirebase;
import com.example.yumyumplanner.remote.firebase.authentication.UserFirebaseModel;
import com.example.yumyumplanner.remote.firebase.authentication.RegisterFirebase;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class AuthenticationRepositryImp implements AuthenticationRepositry{
    private static final String TAG = "AuthenticationRepositry";
    private static AuthenticationRepositryImp authenticationRepositryImp;
    private UserFirebaseModel userFirebaseModel;
    private BackUpDataSourceImp userRepository;

    public static synchronized AuthenticationRepositryImp getInstance(BackUpDataSourceImp userRepository) {
        if (authenticationRepositryImp == null) {
            authenticationRepositryImp = new AuthenticationRepositryImp(userRepository);
        }
        return authenticationRepositryImp;
    }

    private AuthenticationRepositryImp(BackUpDataSourceImp userRepository) {
        this.userFirebaseModel = UserFirebaseModel.getInstance();
        this.userRepository = userRepository;
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
    public void register(String email, String password, String name, RegisterFirebase registerFirebase) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
            registerFirebase.onRegisterFailed("Please enter email, password, and name!!");
        } else {
            userFirebaseModel.getAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Set user's name and email
                            UserProfile userData = new UserProfile(name, email,null);
                            userRepository.saveUserData(userData, null);
                            registerFirebase.onRegisterSuccess();

                        } else {
                            registerFirebase.onRegisterFailed("Sign up is failed, try again");
                        }
                    });
        }

    }

    @Override
    public void forgotPassword(String email, ForgotPasswordCallback callback) {
        userFirebaseModel.getAuth().sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.getMessage());
                    }
                });
    }



}
