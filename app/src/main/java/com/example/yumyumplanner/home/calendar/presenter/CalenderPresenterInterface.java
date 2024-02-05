package com.example.yumyumplanner.home.calendar.presenter;

import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

public interface CalenderPresenterInterface {

    public void getMealsFromCalendar(String  date);
    public void removeFromCalnder(MealCalendar mealCalendar);
}
