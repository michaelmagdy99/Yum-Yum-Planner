package com.example.yumyumplanner.model.backup_repo;

import com.example.yumyumplanner.model.data.UserProfile;

public interface UserDataCallback {
    void onSuccess(UserProfile userProfile);
    void onFailure(String error);
}
