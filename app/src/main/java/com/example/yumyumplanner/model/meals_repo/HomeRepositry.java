package com.example.yumyumplanner.model.meals_repo;

import androidx.lifecycle.LiveData;

import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.network.NetworkCallBack;

import java.util.List;

public interface HomeRepositry {

    void getRandomMeal(NetworkCallBack networkCallBack);
    void getIngredients(NetworkCallBack networkCallBack);

    void getCategories(NetworkCallBack networkCallBack);

    void getCountry(NetworkCallBack networkCallBack);


    void insertMeal(MealsItem mealsItem);
    void deleteMeal(MealsItem mealsItem);
    LiveData<List<MealsItem>> getAllMealsFromLocal();

}
