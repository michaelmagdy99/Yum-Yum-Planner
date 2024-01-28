package com.example.yumyumplanner.database;

import androidx.lifecycle.LiveData;

import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface MealsLocalDataSource {

    void insertMeal(MealsItem mealsItem);
    void deleteMeal(MealsItem mealsItem);
    LiveData<List<MealsItem>> getAllMeals();
}
