package com.example.yumyumplanner.model.backup_repo;

import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface DeleteMealCallback {
    void onSuccess();
    void onFailure(String error);
}
