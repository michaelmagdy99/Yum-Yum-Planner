package com.example.yumyumplanner.home.favourite.presenter;

import com.example.yumyumplanner.model.data.MealsItem;

public interface FavouritePresenterInterface {
    public void getMealsFromDB();
    public void removeFromFav(MealsItem mealsItem);
}
