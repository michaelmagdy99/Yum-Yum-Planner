package com.example.yumyumplanner.model.backup_repo;

import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.data.UserProfile;

import java.util.List;

public interface UserBackUpCallBack {
    void onSuccess(List<MealsItem> userProfile);
    void onFailure(String error);
}
