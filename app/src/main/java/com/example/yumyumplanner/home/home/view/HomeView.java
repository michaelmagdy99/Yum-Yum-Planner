package com.example.yumyumplanner.home.home.view;

import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.CountryItem;
import com.example.yumyumplanner.model.data.FilterItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface HomeView {
    void showData(List<MealsItem> mealsItems);
    void showIngredients(List<IngredientItem> ingredientItems);
    void showCategory(List<CategoriesItem> categoriesItems);

    void showCountry(List<CountryItem> countryItems);
    void showErrorMsg(String error);
    void addMeal(MealsItem mealsItem);
    void showEmptyDataMessage();

}
