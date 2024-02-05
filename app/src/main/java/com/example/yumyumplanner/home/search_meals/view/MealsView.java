package com.example.yumyumplanner.home.search_meals.view;

import com.example.yumyumplanner.model.data.FilterItem;
import com.example.yumyumplanner.model.data.Item;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface MealsView {
    void showMealsFromCountry(List<Item> countries);
    void showMealsFromCategory(List<Item> categories);
    void showEmptyDataMessage();
    void showErrorMsg(String error);
    void showMeals(List<Item> mealsItems);

}
