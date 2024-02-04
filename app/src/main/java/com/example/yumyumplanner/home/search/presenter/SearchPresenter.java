package com.example.yumyumplanner.home.search.presenter;

import com.example.yumyumplanner.model.data.MealsItem;

public interface SearchPresenter {
    public void addToFav(MealsItem meal);

    void getIngredients();
    void getCategories();
    void getCountry();
    void searchByCategoryText(String searchText);
    void searchByCountryText(String searchText);
    void searchByIngrText(String searchText);




}
