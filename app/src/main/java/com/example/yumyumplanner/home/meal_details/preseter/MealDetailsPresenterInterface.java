package com.example.yumyumplanner.home.meal_details.preseter;

import androidx.lifecycle.LiveData;

import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface MealDetailsPresenterInterface {
    public void addToFav(MealsItem meal);
    public void addToCalender(MealCalendar mealCalendar);

    public void getMealDetails(String id);

    void removeFromFav(MealsItem mealsItem);
    LiveData<List<MealsItem>> getMealById(String id);

}
