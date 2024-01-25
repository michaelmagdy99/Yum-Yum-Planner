package com.example.yumyumplanner.model.meals_repo;

import com.example.yumyumplanner.network.NetworkCallBack;

public interface MealsRepositry {

    void getRandomMeal(NetworkCallBack networkCallBack);

}
