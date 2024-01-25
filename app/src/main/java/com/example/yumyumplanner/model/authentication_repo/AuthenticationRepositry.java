package com.example.yumyumplanner.model.authentication_repo;

import com.example.yumyumplanner.firebase.LoginFirebase;
import com.example.yumyumplanner.firebase.RegisterFirebase;

public interface AuthenticationRepositry {

    void login(String email, String password, LoginFirebase loginFirebase);

    void register(String email, String pass, RegisterFirebase registerFirebase);
}
