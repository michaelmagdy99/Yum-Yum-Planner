package com.example.yumyumplanner.remote.firebase.backup;

import android.net.Uri;

import com.example.yumyumplanner.model.data.UserProfile;

public interface BackUpFirebaseDataSource {
    void logOut();
    void getUserData(UserDataCallback userProfile);
    void saveUserData(UserProfile userProfile, Uri imageUri);
}

