package com.example.yumyumplanner.home.ingredient_details.presenter;

import com.example.yumyumplanner.home.home.view.HomeView;
import com.example.yumyumplanner.home.ingredient_details.view.IngredientsView;
import com.example.yumyumplanner.model.data.FilterItem;
import com.example.yumyumplanner.model.data.Item;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.FilterRepoImp;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.NetworkCallBack;

import java.util.List;

public class IngredientsPresenterImp implements IngredientsPresenter, NetworkCallBack {

    private IngredientsView ingredientsView;
    private FilterRepoImp filterRepo;

    public IngredientsPresenterImp(IngredientsView ingredientsView, FilterRepoImp filterRepo){
        this.ingredientsView = ingredientsView;
        this.filterRepo = filterRepo;
    }
    @Override
    public void getMealsByIngredints(String ingredint) {
        filterRepo.getMealsByIngredints(this, ingredint);
    }

    @Override
    public void onSuccessResult(Object result) {
        if (result instanceof List<?>) {
            List<?> resultList = (List<?>) result;

            if (!resultList.isEmpty()) {
                Object item = resultList.get(0);

                if (item instanceof FilterItem) {
                    // Display meals data
                    ingredientsView.showMealsFromIngredints((List<Item>) result);
                }
            } else {
                ingredientsView.showEmptyDataMessage();
            }
        } else {
            ingredientsView.showErrorMsg("Unexpected result type: " + result.getClass().getSimpleName());
        }
    }

    @Override
    public void onFailureResult(String message) {
        ingredientsView.showErrorMsg(message);
    }
}
