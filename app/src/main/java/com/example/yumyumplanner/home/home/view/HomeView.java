package com.example.yumyumplanner.home.home.view;

import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface HomeView {
    public void showData(List<MealsItem> mealsItems);
    public void showErrorMsg(String error);
    public void addMeal(MealsItem mealsItem);
}
