package com.example.yumyumplanner.database;

import androidx.lifecycle.LiveData;

import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface MealsLocalDataSource {

    void insertMeal(MealsItem mealsItem);
    void deleteMeal(MealsItem mealsItem);
    LiveData<List<MealsItem>> getAllMeals();

    LiveData<List<MealCalendar>> getAllMealsFromCalendar(String date);
    void deleteMealFromCalendar(MealCalendar mealCalendar);
    void insertMealToCalendar(MealCalendar mealCalendar);

    LiveData<List<MealsItem>> getMealById(String mealsItemId);


}
