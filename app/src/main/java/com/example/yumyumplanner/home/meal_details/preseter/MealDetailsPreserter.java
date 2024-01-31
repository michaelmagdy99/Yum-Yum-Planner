package com.example.yumyumplanner.home.meal_details.preseter;

import com.example.yumyumplanner.home.meal_details.view.MealsDetailsView;
import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;

public class MealDetailsPreserter implements  MealDetailsPresenterInterface{
    private HomeRepositryImp mealsRepositry;

    private MealsDetailsView view;

    public MealDetailsPreserter(MealsDetailsView view, HomeRepositryImp mealsRepositry){
        this.mealsRepositry = mealsRepositry;
        this.view = view;
    }
    @Override
    public void addToFav(MealsItem meal) {
        mealsRepositry.insertMeal(meal);
    }

    @Override
    public void addToCalender(MealCalendar mealCalendar) {
        mealsRepositry.addToCalendar(mealCalendar);
    }
}
