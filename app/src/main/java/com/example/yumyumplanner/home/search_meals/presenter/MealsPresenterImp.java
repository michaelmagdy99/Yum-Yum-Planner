package com.example.yumyumplanner.home.search_meals.presenter;

import com.example.yumyumplanner.home.ingredient_details.view.IngredientsView;
import com.example.yumyumplanner.home.search_meals.view.MealsView;
import com.example.yumyumplanner.model.data.FilterItem;
import com.example.yumyumplanner.model.meals_repo.FilterRepoImp;
import com.example.yumyumplanner.remote.api.NetworkCallBack;

import java.util.List;

public class MealsPresenterImp implements MealsPresenter, NetworkCallBack {
    private MealsView mealsView;
    private FilterRepoImp filterRepo;

    public MealsPresenterImp(MealsView mealsView, FilterRepoImp filterRepo){
        this.mealsView = mealsView;
        this.filterRepo = filterRepo;
    }
    @Override
    public void getMealsByCountry(String country) {
        filterRepo.getMealsByCountry(this, country);

    }

    @Override
    public void getMealsByCategory(String category) {
        filterRepo.getMealsByCategory(this, category);
    }

    @Override
    public void onSuccessResult(Object result) {
        if (result instanceof List<?>) {
            List<?> resultList = (List<?>) result;

            if (!resultList.isEmpty()) {
                Object item = resultList.get(0);

                if (item instanceof FilterItem) {
                    // Display meals data
                    mealsView.showMealsFromCountry((List<FilterItem>) result);
                }
            } else {
                mealsView.showEmptyDataMessage();
            }
        } else {
            mealsView.showErrorMsg("Unexpected result type: " + result.getClass().getSimpleName());
        }
    }

    @Override
    public void onFailureResult(String message) {
        mealsView.showErrorMsg(message);
    }
}
