package com.example.yumyumplanner.model.backup_repo;

import com.example.yumyumplanner.model.data.UserProfile;

public interface UserDataCallback {
    void onUserDataLoaded(UserProfile userProfile);
    void onDataNotFound(Exception e);

    void success();
    void error(String string);
}
