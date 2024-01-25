package com.example.yumyumplanner.network;

import com.example.yumyumplanner.model.data.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealsRemoteWebService {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();
}
