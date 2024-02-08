package com.example.yumyumplanner.home.favourite.presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.yumyumplanner.home.favourite.view.FavouriteView;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.data.UserProfile;
import com.example.yumyumplanner.model.meals_repo.HomeRepositry;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;
import com.example.yumyumplanner.remote.firebase.backup.DeleteMealCallback;
import com.example.yumyumplanner.remote.firebase.backup.MealsBackUpCallBack;

import java.util.List;

public class FavouritePresenter implements FavouritePresenterInterface{

    private FavouriteView view ;
    private HomeRepositry repository;
    private BackUpDataSourceImp backUpRepositoryImp;

    public FavouritePresenter(FavouriteView view, HomeRepositry repository, BackUpDataSourceImp backUpRepositoryImp){
        this.view = view;
        this.repository = repository;
        this.backUpRepositoryImp = backUpRepositoryImp;
    }
    @Override
    public void getMealsFromDB() {
        view.showData(repository.getAllMealsFromLocal());

        //retrive backup meal
        Log.i("TAG", "getData: " + UserProfile.getCurrentUserId() );

    }
    @Override
    public void removeFromFav(MealsItem mealsItem) {
        String userId = UserProfile.getCurrentUserId();
        String mealId = mealsItem.getIdMeal();
        Log.i("TAG", "removeFromFav: " + mealId);
        if (userId != null && mealId != null) {
            backUpRepositoryImp.deleteMeal(userId, mealId, new DeleteMealCallback() {
                @Override
                public void onSuccess() {
                    repository.deleteMeal(mealsItem);

                    view.afterRemove();
                }
                @Override
                public void onFailure(String error) {
                    view.showErrorMsg(error);
                }
            });
        } else {
            view.showErrorMsg("No User, Login First");
        }
    }
}
