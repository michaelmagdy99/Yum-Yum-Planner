package com.example.yumyumplanner.model.meals_repo;

import com.example.yumyumplanner.remote.api.NetworkCallBack;

public interface FilterRepo {
    void getMealsByIngredints(NetworkCallBack networkCallBack,String ingredints);
    void getMealsByCountry(NetworkCallBack networkCallBack,String country);
    void getMealsByCategory(NetworkCallBack networkCallBack,String category);
    void getMealsById(NetworkCallBack networkCallBack,String id);

    void searchMealsByName(NetworkCallBack networkCallBack,String mealName);
}
