package com.example.yumyumplanner.home.calendar.view;

import androidx.lifecycle.LiveData;

import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface CalenderView {

    public void showData(LiveData<List<MealCalendar>> allMealsCaleander);
    public void showErrorMsg(String error);
    public void deleteMeals(MealCalendar mealCalendar);
}
