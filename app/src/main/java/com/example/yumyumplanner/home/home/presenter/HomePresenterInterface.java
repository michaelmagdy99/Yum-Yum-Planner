package com.example.yumyumplanner.home.home.presenter;

import com.example.yumyumplanner.model.data.MealsItem;

public interface HomePresenterInterface {
    public void getMeals();
    public void addToFav(MealsItem meal);

    void getIngredients();
    void getCategories();
    void getCountry();
    void removeFromFav(MealsItem mealsItem);

}
