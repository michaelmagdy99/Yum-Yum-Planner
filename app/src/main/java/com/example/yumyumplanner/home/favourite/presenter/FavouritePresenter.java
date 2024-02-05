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
        repository.getAllMealsFromLocal().observe((LifecycleOwner) view, new Observer<List<MealsItem>>() {
            @Override
            public void onChanged(List<MealsItem> mealsItems) {
                view.showData(mealsItems);
            }
        });
        //retrive backup meal
        Log.i("TAG", "getData: " + UserProfile.getCurrentUserId() );
        backUpRepositoryImp.retrieveMeals(UserProfile.getCurrentUserId(), new MealsBackUpCallBack() {

            @Override
            public void onSuccess(List<MealsItem> mealsItemsList) {
                view.showData(mealsItemsList);
            }

            @Override
            public void onFailure(String error) {
                view.showErrorMsg(error);
            }
        });
    }
    @Override
    public void removeFromFav(MealsItem mealsItem) {
        repository.deleteMeal(mealsItem);

        String userId = UserProfile.getCurrentUserId();
        String mealId = mealsItem.getMealIdInFirabse();
        Log.i("TAG", "removeFromFav: " + mealId);
        if (userId != null && mealId != null) {
            backUpRepositoryImp.deleteMeal(userId, mealId, new DeleteMealCallback() {
                @Override
                public void onSuccess() {
                    view.afterRemove();
                }
                @Override
                public void onFailure(String error) {
                    view.showErrorMsg(error);
                }
            });
        } else {
            view.showErrorMsg("NO User, Login First");
        }
    }
}
