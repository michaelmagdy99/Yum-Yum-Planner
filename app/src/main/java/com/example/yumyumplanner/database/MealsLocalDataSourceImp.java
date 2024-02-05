package com.example.yumyumplanner.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public class MealsLocalDataSourceImp implements MealsLocalDataSource{

    private MealDAO dao ;
    private static  MealsLocalDataSourceImp mealsLocalDataSource = null;
    private LiveData<List<MealsItem>> storedMeals ;


    private MealsLocalDataSourceImp(Context context){
        MealDatabase db = MealDatabase.getInstance(context.getApplicationContext());
        dao = db.getMealDAO();
        storedMeals = dao.getAllMeal();
    }

    public static MealsLocalDataSourceImp getInstance(Context context){
        if(mealsLocalDataSource == null){
            mealsLocalDataSource = new MealsLocalDataSourceImp(context);
        }
        return mealsLocalDataSource;
    }

    @Override
    public void insertMeal(MealsItem mealsItem) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insert(mealsItem);
            }
        }).start();
    }

    @Override
    public void deleteMeal(MealsItem mealsItem) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.delete(mealsItem);
            }
        }).start();
    }

    @Override
    public LiveData<List<MealsItem>> getAllMeals() {
        return storedMeals;
    }

    @Override
    public LiveData<List<MealCalendar>> getAllMealsFromCalendar(String date) {
        return dao.getAllMealsFromCalendar(date);
    }

    @Override
    public void deleteMealFromCalendar(MealCalendar mealCalendar) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.deleteMealFromCalendar(mealCalendar);
            }
        }).start();
    }

    @Override
    public void insertMealToCalendar(MealCalendar mealCalendar) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insertMealtoCalendar(mealCalendar);
            }
        }).start();
    }



}
