package com.example.yumyumplanner.home.profile.presenter;

import android.content.Context;
import android.net.Uri;

import com.example.yumyumplanner.home.profile.view.ProfileView;
import com.example.yumyumplanner.model.authentication_repo.AuthenticationRepositry;
import com.google.firebase.auth.FirebaseUser;

public class ProfilePresenterImp implements ProfilePresenter{

    private ProfileView view;
    private AuthenticationRepositry authenticationRepositry;
    private static ProfilePresenterImp profilePresenterImp;

    public static ProfilePresenterImp getInstance(ProfileView profileView) {
        if (profilePresenterImp == null) {
            profilePresenterImp = new ProfilePresenterImp(profileView);
        }
        return profilePresenterImp;
    }

    private ProfilePresenterImp(ProfileView view) {
        this.view = view;
    }
    @Override
    public void logOut() {
        authenticationRepositry.logOut();
    }

}
