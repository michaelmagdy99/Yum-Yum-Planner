package com.example.yumyumplanner.home.calendar.view;

import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

public interface OnClickPlanListener {

    void onItemClick(MealCalendar mealsItem);

    void onDetailsItemClick(MealCalendar mealsItem);
}
