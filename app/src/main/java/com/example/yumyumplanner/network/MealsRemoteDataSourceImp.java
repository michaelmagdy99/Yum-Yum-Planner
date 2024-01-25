package com.example.yumyumplanner.network;

import com.example.yumyumplanner.model.data.MealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImp implements MealsRemoteDataSource{

    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private MealsRemoteWebService mealsRemoteWebService;
    private static MealsRemoteDataSourceImp mealsRemoteDataSource;

    public static synchronized MealsRemoteDataSourceImp getInstance() {
        if (mealsRemoteDataSource == null) {
            mealsRemoteDataSource = new MealsRemoteDataSourceImp();
        }
        return mealsRemoteDataSource;
    }
    private MealsRemoteDataSourceImp() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mealsRemoteWebService = retrofit.create(MealsRemoteWebService.class);
    }

    @Override
    public void makeNetworkCall(NetworkCallBack networkCallBack) {
        Call<MealResponse> randomMealCall = mealsRemoteWebService.getRandomMeal();
        randomMealCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()){
                    networkCallBack.onSuccessResult(response.body().meals);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkCallBack.onFailureResult(t.getMessage());
            }
        });
    }
}
