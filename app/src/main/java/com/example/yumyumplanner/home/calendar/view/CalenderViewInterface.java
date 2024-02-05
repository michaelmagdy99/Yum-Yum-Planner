package com.example.yumyumplanner.home.calendar.view;

import androidx.lifecycle.LiveData;

import com.example.yumyumplanner.model.data.MealCalendar;

import java.util.List;

public interface CalenderViewInterface {

    public void showData(List<MealCalendar> allMealsCaleander);
    public void showErrorMsg(String error);
    public void deleteMeals(MealCalendar mealCalendar);

    void afterRemove();
}
