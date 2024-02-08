package com.example.yumyumplanner.remote.firebase.backup;

import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface MealsBackUpCallBack {
    void onSuccess(List<MealsItem> mealsItems);
    void onFailure(String error);
}
