package com.example.yumyumplanner.network;

import com.example.yumyumplanner.model.data.MealResponse;

import retrofit2.Call;

public interface MealsRemoteDataSource {
    void makeNetworkCall(NetworkCallBack networkCallBack);

}
