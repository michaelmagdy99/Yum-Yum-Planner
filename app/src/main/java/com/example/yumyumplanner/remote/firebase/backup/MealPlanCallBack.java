package com.example.yumyumplanner.remote.firebase.backup;

import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface MealPlanCallBack {

    void onSuccess(List<MealCalendar> mealCalendars);
    void onFailure(String error);
}
