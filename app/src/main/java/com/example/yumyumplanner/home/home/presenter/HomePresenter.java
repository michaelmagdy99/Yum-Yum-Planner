package com.example.yumyumplanner.home.home.presenter;

import com.example.yumyumplanner.home.home.view.HomeView;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.MealsRepositryImp;
import com.example.yumyumplanner.network.NetworkCallBack;

import java.util.List;

public class HomePresenter implements HomePresenterInterface, NetworkCallBack {

    private HomeView homeView;
    private MealsRepositryImp mealsRepositry;

    public HomePresenter(HomeView homeView, MealsRepositryImp mealsRepositry){
        this.homeView = homeView;
        this.mealsRepositry = mealsRepositry;
    }

    @Override
    public void getMeals() {
        mealsRepositry.getRandomMeal(this);
    }

    @Override
    public void addToFav(MealsItem meal) {

    }

    @Override
    public void onSuccessResult(List<MealsItem> meals) {
        homeView.showData(meals);
    }

    @Override
    public void onFailureResult(String message) {
        homeView.showErrorMsg(message);
    }
}
