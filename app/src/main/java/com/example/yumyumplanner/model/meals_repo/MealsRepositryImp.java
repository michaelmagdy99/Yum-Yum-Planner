package com.example.yumyumplanner.model.meals_repo;

import com.example.yumyumplanner.network.MealsRemoteDataSource;
import com.example.yumyumplanner.network.MealsRemoteDataSourceImp;
import com.example.yumyumplanner.network.NetworkCallBack;

public class MealsRepositryImp implements MealsRepositry {
    private static MealsRepositryImp mealsRepositry;

    private MealsRemoteDataSource mealsRemoteDataSource;
    private MealsRepositryImp(MealsRemoteDataSource mealsRemoteDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
    }
    public static MealsRepositryImp getInstance(MealsRemoteDataSource mealsRemoteDataSource){
        if (mealsRepositry == null){
            mealsRepositry = new MealsRepositryImp(mealsRemoteDataSource);
        }
        return mealsRepositry;
    }

    @Override
    public void getRandomMeal(NetworkCallBack networkCallBack) {
        mealsRemoteDataSource.makeNetworkCall(networkCallBack);
    }
}
