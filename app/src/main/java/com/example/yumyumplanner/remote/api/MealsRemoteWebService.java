package com.example.yumyumplanner.remote.api;

import com.example.yumyumplanner.model.data.CategoriesResponse;
import com.example.yumyumplanner.model.data.CountryResponse;
import com.example.yumyumplanner.model.data.FilterMealResponse;
import com.example.yumyumplanner.model.data.IngredientsResponse;
import com.example.yumyumplanner.model.data.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsRemoteWebService {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();
    @GET("list.php?i=list")
    public Call<IngredientsResponse> getIngredients();
    @GET("categories.php")
    public Call<CategoriesResponse> getCategories();
    @GET("list.php?a=list")
    public Call<CountryResponse> getCuisines();

    @GET("filter.php")
    public Call<FilterMealResponse> getMealsByIngredient(@Query("i") String ingredient);
    @GET("filter.php")
    public Call<FilterMealResponse> getMealsByCategory(@Query("c") String category);
    @GET("filter.php")
    public Call<FilterMealResponse> getMealsByArea(@Query("a") String cuisine);

    @GET("lookup.php")
    public Call<MealResponse> getMealById(@Query("i") String id);

    @GET("search.php")
    public Call<MealResponse>searchByName(@Query("s") String mealName);

}
