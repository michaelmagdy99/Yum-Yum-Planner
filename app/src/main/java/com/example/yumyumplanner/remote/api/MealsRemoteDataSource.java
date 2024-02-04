package com.example.yumyumplanner.remote.api;

import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.CountryItem;
import com.example.yumyumplanner.model.data.FilterItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.MealResponse;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface MealsRemoteDataSource {
    void makeRandomMealCall(NetworkCallBack<List<MealsItem>> networkCallBack);
    void makeIngredientsCall(NetworkCallBack<List<IngredientItem>> networkCallBack);
    void makeCategoriesCall(NetworkCallBack<List<CategoriesItem>> networkCallBack);
    void makeCountryCall(NetworkCallBack<List<CountryItem>> networkCallBack);

    void getMealFromIngredintsCall(NetworkCallBack<List<FilterItem>> networkCallBack,String ingredinat);

    void getMealFromCountryCall(NetworkCallBack<List<FilterItem>> networkCallBack,String country);

    void getMealFromCategoryCall(NetworkCallBack<List<FilterItem>> networkCallBack,String category);
    void getMealbyIdCall(NetworkCallBack<List<MealsItem>> networkCallBack,String id);

    void searchMealbyName(NetworkCallBack<List<MealResponse>> networkCallBack, String mealName);


}
