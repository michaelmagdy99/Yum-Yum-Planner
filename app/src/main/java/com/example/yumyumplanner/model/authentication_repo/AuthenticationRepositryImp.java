package com.example.yumyumplanner.model.authentication_repo;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import com.example.yumyumplanner.remote.firebase.ForgotPasswordCallback;
import com.example.yumyumplanner.remote.firebase.LoginFirebase;
import com.example.yumyumplanner.remote.firebase.UserFirebaseModel;
import com.example.yumyumplanner.remote.firebase.RegisterFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public void logOut() {
        userFirebaseModel.getAuth().signOut();
    }

}
