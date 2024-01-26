package com.example.yumyumplanner.model.meals_repo;

import com.example.yumyumplanner.network.NetworkCallBack;

public interface HomeRepositry {

    void getRandomMeal(NetworkCallBack networkCallBack);
    void getIngredients(NetworkCallBack networkCallBack);

    void getCategories(NetworkCallBack networkCallBack);

    void getCountry(NetworkCallBack networkCallBack);

}
