package com.example.yumyumplanner.home.search.view;

import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.CountryItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface SearchView {

    void showIngredients(List<IngredientItem> ingredientItems);
    void showCategory(List<CategoriesItem> categoriesItems);
    void showCounrty(List<CountryItem> countryItem);

    void showErrorMsg(String error);
    void addMeal(MealsItem mealsItem);
    void showEmptyDataMessage();
}
