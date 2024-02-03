package com.example.yumyumplanner.model.meals_repo;

import com.example.yumyumplanner.database.MealsLocalDataSource;
import com.example.yumyumplanner.remote.api.MealsRemoteDataSource;
import com.example.yumyumplanner.remote.api.NetworkCallBack;

public class FilterRepoImp implements FilterRepo{

    private static FilterRepoImp filterRepo;

    private MealsRemoteDataSource mealsRemoteDataSource;

    private FilterRepoImp(MealsRemoteDataSource mealsRemoteDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
    }
    public static FilterRepoImp getInstance(MealsRemoteDataSource mealsRemoteDataSource){
        if (filterRepo == null){
            filterRepo = new FilterRepoImp(mealsRemoteDataSource);
        }
        return filterRepo;
    }

    @Override
    public void getMealsByIngredints(NetworkCallBack networkCallBack, String ngredints) {
        mealsRemoteDataSource.getMealFromIngredintsCall(networkCallBack, ngredints);
    }

    @Override
    public void getMealsByCountry(NetworkCallBack networkCallBack, String country) {
        mealsRemoteDataSource.getMealFromCountryCall(networkCallBack, country);
    }

    @Override
    public void getMealsByCategory(NetworkCallBack networkCallBack, String category) {
        mealsRemoteDataSource.getMealFromCategoryCall(networkCallBack, category);
    }

    @Override
    public void getMealsById(NetworkCallBack networkCallBack, String id) {
        mealsRemoteDataSource.getMealbyIdCall(networkCallBack, id);
    }
}
