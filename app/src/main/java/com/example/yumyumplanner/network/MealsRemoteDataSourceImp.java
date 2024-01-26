// MealsRemoteDataSourceImp.java
package com.example.yumyumplanner.network;

import com.example.yumyumplanner.model.data.CategoriesResponse;
import com.example.yumyumplanner.model.data.CountryResponse;
import com.example.yumyumplanner.model.data.IngredientsResponse;
import com.example.yumyumplanner.model.data.MealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImp implements MealsRemoteDataSource {

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
    public void makeRandomMealCall(NetworkCallBack networkCallBack) {
        Call<MealResponse> randomMealCall = mealsRemoteWebService.getRandomMeal();
        randomMealCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().meals);
                } else {
                    networkCallBack.onFailureResult("Failed to get random meal. " +
                            "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkCallBack.onFailureResult("Network request failed. " +
                        "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void makeIngredientsCall(NetworkCallBack networkCallBack) {
        Call<IngredientsResponse> ingredientsCall = mealsRemoteWebService.getIngredients();
        ingredientsCall.enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().ingredientItems);
                } else {
                    networkCallBack.onFailureResult("Failed to get ingredients. " +
                            "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable t) {
                networkCallBack.onFailureResult("Network request failed. " +
                        "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void makeCategoriesCall(NetworkCallBack networkCallBack) {
        Call<CategoriesResponse> categoriesCall = mealsRemoteWebService.getCategories();
        categoriesCall.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().getCategories());
                } else {
                    networkCallBack.onFailureResult("Failed to get categories. " +
                            "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                networkCallBack.onFailureResult("Network request failed. " +
                        "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void makeCountryCall(NetworkCallBack networkCallBack) {
        Call<CountryResponse> cuisinesCall = mealsRemoteWebService.getCuisines();
        cuisinesCall.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().getCountry());
                } else {
                    networkCallBack.onFailureResult("Failed to get cuisines. " +
                            "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                networkCallBack.onFailureResult("Network request failed. " +
                        "Error: " + t.getMessage());
            }
        });
    }
}
