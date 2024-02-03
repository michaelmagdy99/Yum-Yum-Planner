package com.example.yumyumplanner.home.meal_details.preseter;

import com.example.yumyumplanner.home.meal_details.view.MealsDetailsView;
import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.FilterRepoImp;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.NetworkCallBack;

import java.util.List;

public class MealDetailsPreserter implements  MealDetailsPresenterInterface, NetworkCallBack {
    private HomeRepositryImp mealsRepositry;

    private FilterRepoImp filterRepo;

    private MealsDetailsView view;

    public MealDetailsPreserter(MealsDetailsView view, HomeRepositryImp mealsRepositry, FilterRepoImp filterRepo){
        this.mealsRepositry = mealsRepositry;
        this.view = view;
        this.filterRepo = filterRepo;
    }
    @Override
    public void addToFav(MealsItem meal) {
        mealsRepositry.insertMeal(meal);
    }

    @Override
    public void addToCalender(MealCalendar mealCalendar) {
        mealsRepositry.addToCalendar(mealCalendar);
    }

    @Override
    public void getMealDetails(String id) {
        filterRepo.getMealsById(this, id);
    }

    @Override
    public void onSuccessResult(Object result) {
        if (result instanceof List<?>) {
            List<?> resultList = (List<?>) result;

            if (!resultList.isEmpty()) {
                Object item = resultList.get(0);

                if (item instanceof MealsItem) {
                    // Display meals data
                    view.showData((List<MealsItem>) result);
                }
                else {
                    view.showEmptyDataMessage();
                }
            } else {
                view.showErrorMsg("Unexpected result type: " + result.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void onFailureResult(String message) {
        view.showErrorMsg(message);

    }
}
