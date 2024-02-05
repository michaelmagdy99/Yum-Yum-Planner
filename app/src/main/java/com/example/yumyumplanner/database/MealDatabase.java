package com.example.yumyumplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

@Database(entities = {MealsItem.class, MealCalendar.class}, version = 1)
public abstract class MealDatabase extends RoomDatabase {

    private static MealDatabase instance = null;

    public abstract MealDAO getMealDAO() ;

    public static synchronized MealDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext() , MealDatabase.class ,"mealdb")
                    .build();
        }
        return instance;
    }
    public void clearAllData() {
        new Thread(() -> {
            getMealDAO().deleteAllPlan();
            getMealDAO().deleteAllFav();
        }).start();
    }
}
