package com.example.yumyumplanner.home.search_meals.view;

import com.example.yumyumplanner.model.data.FilterItem;

import java.util.List;

public interface MealsView {
    void showMealsFromCountry(List<FilterItem> countries);
    void showMealsFromCategory(List<FilterItem> categories);
    void showEmptyDataMessage();
    void showErrorMsg(String error);
}
