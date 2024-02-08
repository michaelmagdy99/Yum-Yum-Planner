package com.example.yumyumplanner.model.meals_repo;

import androidx.lifecycle.LiveData;

import com.example.yumyumplanner.database.MealsLocalDataSource;
import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.remote.api.MealsRemoteDataSource;
import com.example.yumyumplanner.remote.api.NetworkCallBack;

import java.util.List;

public class HomeRepositryImp implements HomeRepositry {
    private static HomeRepositryImp mealsRepositry;

    private MealsRemoteDataSource mealsRemoteDataSource;


    private MealsLocalDataSource mealsLocalDataSource;
    private HomeRepositryImp(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealsLocalDataSource = mealsLocalDataSource;
    }
    public static HomeRepositryImp getInstance(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource){
        if (mealsRepositry == null){
            mealsRepositry = new HomeRepositryImp(mealsRemoteDataSource, mealsLocalDataSource);
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

    @Override
    public void insertMeal(MealsItem mealsItem) {
        mealsLocalDataSource.insertMeal(mealsItem);
    }

    @Override
    public void deleteMeal(MealsItem mealsItem) {
        mealsLocalDataSource.deleteMeal(mealsItem);
    }

    @Override
    public LiveData<List<MealsItem>> getAllMealsFromLocal() {
        return mealsLocalDataSource.getAllMeals();
    }

    @Override
    public void addToCalendar(MealCalendar mealCalendar) {
        mealsLocalDataSource.insertMealToCalendar(mealCalendar);
    }

    @Override
    public void deleteMealFromCalendar(MealCalendar mealCalendar) {
        mealsLocalDataSource.deleteMealFromCalendar(mealCalendar);
    }

    @Override
    public LiveData<List<MealCalendar>> getAllMealsFromCalendar(String date) {
        return mealsLocalDataSource.getAllMealsFromCalendar(date);
    }

    @Override
    public LiveData<List<MealsItem>> getMealById(String date) {
        return mealsLocalDataSource.getMealById(date);
    }


}
