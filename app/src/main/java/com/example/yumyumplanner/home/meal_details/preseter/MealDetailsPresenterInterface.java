package com.example.yumyumplanner.home.meal_details.preseter;

import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

public interface MealDetailsPresenterInterface {
    public void addToFav(MealsItem meal);
    public void addToCalender(MealCalendar mealCalendar);

}
