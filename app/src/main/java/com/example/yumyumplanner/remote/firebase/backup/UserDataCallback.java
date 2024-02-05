package com.example.yumyumplanner.remote.firebase.backup;

import com.example.yumyumplanner.model.data.UserProfile;

public interface UserDataCallback {
    void onSuccess(UserProfile userProfile);
    void onFailure(String error);
}
