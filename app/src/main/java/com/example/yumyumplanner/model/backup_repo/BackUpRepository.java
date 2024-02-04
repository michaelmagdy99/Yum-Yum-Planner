package com.example.yumyumplanner.model.backup_repo;

import android.net.Uri;

import com.example.yumyumplanner.model.data.UserProfile;

public interface BackUpRepository {
    void logOut();
    void getUserData(UserDataCallback userProfile);
    void saveUserData(UserProfile userProfile, Uri imageUri);
}

