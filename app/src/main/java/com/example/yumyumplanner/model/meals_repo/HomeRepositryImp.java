package com.example.yumyumplanner.model.meals_repo;

import com.example.yumyumplanner.network.MealsRemoteDataSource;
import com.example.yumyumplanner.network.NetworkCallBack;

public class HomeRepositryImp implements HomeRepositry {
    private static HomeRepositryImp mealsRepositry;

    private MealsRemoteDataSource mealsRemoteDataSource;
    private HomeRepositryImp(MealsRemoteDataSource mealsRemoteDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
    }
    public static HomeRepositryImp getInstance(MealsRemoteDataSource mealsRemoteDataSource){
        if (mealsRepositry == null){
            mealsRepositry = new HomeRepositryImp(mealsRemoteDataSource);
        }
        return mealsRepositry;
    }

    @Override
    public void getRandomMeal(NetworkCallBack networkCallBack) {
        mealsRemoteDataSource.makeRandomMealCall(networkCallBack);
    }

    @Override
    public void getIngredients(NetworkCallBack networkCallBack) {
        mealsRemoteDataSource.makeIngredientsCall(networkCallBack);
    }

    @Override
    public void getCategories(NetworkCallBack networkCallBack) {
        mealsRemoteDataSource.makeCategoriesCall(networkCallBack);
    }

    @Override
    public void getCountry(NetworkCallBack networkCallBack) {
        mealsRemoteDataSource.makeCountryCall(networkCallBack);
    }
}
