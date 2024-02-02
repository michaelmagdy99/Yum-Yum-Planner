package com.example.yumyumplanner.model.backup_repo;

import android.net.Uri;

import com.example.yumyumplanner.model.data.UserProfile;

import io.reactivex.rxjava3.core.Single;

public interface BackUpRepository {
    void logOut();
    void getUserData(UserDataCallback callback);
    void saveUserData(UserProfile userProfile, Uri imageUri, UserDataCallback callback);
}

