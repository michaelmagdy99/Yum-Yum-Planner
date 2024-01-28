package com.example.yumyumplanner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

@Dao
public interface MealDAO {

    @Query("SELECT * FROM meal")
    LiveData<List<MealsItem>> getAllMeal();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MealsItem mealsItem);

    @Delete
    void delete(MealsItem mealsItem);
}
