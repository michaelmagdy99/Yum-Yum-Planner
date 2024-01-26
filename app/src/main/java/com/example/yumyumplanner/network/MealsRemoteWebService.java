package com.example.yumyumplanner.network;

import com.example.yumyumplanner.model.data.CategoriesResponse;
import com.example.yumyumplanner.model.data.CountryResponse;
import com.example.yumyumplanner.model.data.IngredientsResponse;
import com.example.yumyumplanner.model.data.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealsRemoteWebService {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();
    @GET("list.php?i=list")
    public Call<IngredientsResponse> getIngredients();
    @GET("categories.php")
    public Call<CategoriesResponse> getCategories();
    @GET("list.php?a=list")
    public Call<CountryResponse> getCuisines();
}
