package com.example.yumyumplanner.network;

import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.CountryItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface MealsRemoteDataSource {
    void makeRandomMealCall(NetworkCallBack<List<MealsItem>> networkCallBack);
    void makeIngredientsCall(NetworkCallBack<List<IngredientItem>> networkCallBack);
    void makeCategoriesCall(NetworkCallBack<List<CategoriesItem>> networkCallBack);
    void makeCountryCall(NetworkCallBack<List<CountryItem>> networkCallBack);
}
