package com.example.yumyumplanner.home.calendar.presenter;

import com.example.yumyumplanner.home.calendar.view.CalenderViewInterface;
import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;

public class CalenderPresenter implements CalenderPresenterInterface {

    private CalenderViewInterface calenderView;
    private HomeRepositryImp repositry;

    public CalenderPresenter(CalenderViewInterface calenderView, HomeRepositryImp repositry){
        this.calenderView = calenderView;
        this.repositry = repositry;
    }
    @Override
    public void getMealsFromCalendar(String date) {
        calenderView.showData(repositry.getAllMealsFromCalendar(date));
    }

    @Override
    public void removeFromCalnder(MealCalendar mealCalendar) {
        repositry.deleteMealFromCalendar(mealCalendar);
    }
}
