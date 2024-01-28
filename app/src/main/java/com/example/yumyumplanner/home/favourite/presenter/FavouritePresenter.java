package com.example.yumyumplanner.home.favourite.presenter;

import com.example.yumyumplanner.home.favourite.view.FavouriteView;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.HomeRepositry;

public class FavouritePresenter implements FavouritePresenterInterface{

    private FavouriteView view ;
    private HomeRepositry repository;

    public FavouritePresenter(FavouriteView view, HomeRepositry repository){
        this.view = view;
        this.repository = repository;
    }
    @Override
    public void getMealsFromDB() {
        view.showData(repository.getAllMealsFromLocal());
    }

    @Override
    public void removeFromFav(MealsItem mealsItem) {
        repository.deleteMeal(mealsItem);
    }
}
