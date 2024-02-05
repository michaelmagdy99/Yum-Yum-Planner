package com.example.yumyumplanner.home.meal_details.view;

import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface MealsDetailsView {
    void addMealToFav(MealsItem mealsItem);
    void addMealToCalendar(MealCalendar mealCalendar);

    void showEmptyDataMessage();
    void showData(List<MealsItem> mealsItems);
    void showErrorMsg(String error);

}
