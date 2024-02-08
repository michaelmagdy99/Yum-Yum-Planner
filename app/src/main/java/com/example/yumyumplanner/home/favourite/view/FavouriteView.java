package com.example.yumyumplanner.home.favourite.view;

import androidx.lifecycle.LiveData;

import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public interface FavouriteView {
    public void showData(LiveData<List<MealsItem>> allMealsFromLocal);
    public void showErrorMsg(String error);
    public void deleteMeals(MealsItem mealsItem);

    void afterRemove();
}
