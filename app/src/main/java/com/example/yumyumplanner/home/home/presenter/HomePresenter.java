package com.example.yumyumplanner.home.home.presenter;

import com.example.yumyumplanner.home.home.view.HomeView;
import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.NetworkCallBack;

import java.util.List;

public class HomePresenter implements HomePresenterInterface, NetworkCallBack {

    private HomeView homeView;
    private HomeRepositryImp mealsRepositry;

    public HomePresenter(HomeView homeView, HomeRepositryImp mealsRepositry){
        this.homeView = homeView;
        this.mealsRepositry = mealsRepositry;
    }

    @Override
    public void getMeals() {
        mealsRepositry.getRandomMeal(this);
    }

    @Override
    public void addToFav(MealsItem meal) {
            mealsRepositry.insertMeal(meal);
    }

    @Override
    public void getIngredients() {
        mealsRepositry.getIngredients(this);

    }

    @Override
    public void getCategories() {
        mealsRepositry.getCategories(this);

    }

    @Override
    public void getCountry() {
        mealsRepositry.getCountry(this);
    }


    @Override
    public void onSuccessResult(Object result) {
        if (result instanceof List<?>) {
            List<?> resultList = (List<?>) result;

            if (!resultList.isEmpty()) {
                Object item = resultList.get(0);

                if (item instanceof MealsItem) {
                    // Display meals data
                    homeView.showData((List<MealsItem>) result);
                } else if (item instanceof IngredientItem) {
                    // Display ingredients data
                    homeView.showIngredients((List<IngredientItem>) result);
                }
                else if (item instanceof CategoriesItem) {
                    // Display CategoriesItem data
                    homeView.showCategory((List<CategoriesItem>) result);
            } else {
                homeView.showEmptyDataMessage();
            }
        } else {
                homeView.showErrorMsg("Unexpected result type: " + result.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void onFailureResult(String message) {
        homeView.showErrorMsg(message);
    }
}
