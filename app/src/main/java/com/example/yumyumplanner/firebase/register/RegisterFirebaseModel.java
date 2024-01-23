package com.example.yumyumplanner.firebase.register;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.firebase.login.LoginFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFirebaseModel {
    public void registerUser(String email, String password, RegisterFirebase listener) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            listener.onRegisterFailed("Please enter email and Password!!");

        } else {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                listener.onRegisterSuccess();
                            }
                            else {
                                listener.onRegisterFailed("Sign up is failed, try again");
                            }
                        }
                    });
        }
    }
}
