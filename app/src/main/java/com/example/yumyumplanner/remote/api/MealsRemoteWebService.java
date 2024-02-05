package com.example.yumyumplanner.remote.api;

import com.example.yumyumplanner.model.data.CategoriesResponse;
import com.example.yumyumplanner.model.data.CountryResponse;
import com.example.yumyumplanner.model.data.FilterMealResponse;
import com.example.yumyumplanner.model.data.IngredientsResponse;
import com.example.yumyumplanner.model.data.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsRemoteWebService {
    @GET("random.php")
    Single<MealResponse> getRandomMeal();
    @GET("list.php?i=list")
    Single<IngredientsResponse> getIngredients();
    @GET("categories.php")
    Single<CategoriesResponse> getCategories();
    @GET("list.php?a=list")
    Single<CountryResponse> getArea();

    @GET("filter.php")
    Single<FilterMealResponse> getMealsByIngredient(@Query("i") String ingredient);
    @GET("filter.php")
    Single<FilterMealResponse> getMealsByCategory(@Query("c") String category);
    @GET("filter.php")
    Single<FilterMealResponse> getMealsByArea(@Query("a") String area);

    @GET("lookup.php")
    Single<MealResponse> getMealById(@Query("i") String id);

    @GET("search.php")
    Single<MealResponse>searchByName(@Query("s") String mealName);

}
