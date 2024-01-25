package com.example.yumyumplanner.network;

import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface NetworkCallBack {
    public void onSuccessResult(List<MealsItem> meals);
    public void onFailureResult(String message);
}
