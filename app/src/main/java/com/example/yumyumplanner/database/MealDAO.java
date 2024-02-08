package com.example.yumyumplanner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

@Dao
public interface MealDAO {

    @Query("SELECT * FROM meal")
    LiveData<List<MealsItem>> getAllMeal();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MealsItem mealsItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMeals(List<MealsItem> mealsItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllPlan(List<MealCalendar> mealsItemsCal);
    @Delete
    void delete(MealsItem mealsItem);

    @Query("SELECT * FROM meal_plan WHERE date = :date")
    LiveData<List<MealCalendar>> getAllMealsFromCalendar(String date);

    @Query("SELECT * FROM meal WHERE idMeal= :id")
    LiveData<List<MealsItem>> getMealsById(String id);

    @Delete
    void deleteMealFromCalendar(MealCalendar mealCalendar);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertMealtoCalendar(MealCalendar mealCalendar);

    @Query("DELETE FROM meal_plan")
    void deleteAllPlan();
    @Query("DELETE FROM meal")
    void deleteAllFav();
}
