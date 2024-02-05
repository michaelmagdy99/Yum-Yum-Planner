package com.example.yumyumplanner.home.calendar.presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.yumyumplanner.home.calendar.view.CalenderViewInterface;
import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.data.UserProfile;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;
import com.example.yumyumplanner.remote.firebase.backup.DeleteMealCallback;
import com.example.yumyumplanner.remote.firebase.backup.MealPlanCallBack;
import com.example.yumyumplanner.remote.firebase.backup.MealsBackUpCallBack;

import java.util.List;

public class CalenderPresenter implements CalenderPresenterInterface {

    private CalenderViewInterface calenderView;
    private HomeRepositryImp repositry;
    private BackUpDataSourceImp backUpRepositoryImp;


    public CalenderPresenter(CalenderViewInterface calenderView, HomeRepositryImp repositry, BackUpDataSourceImp backUpRepositoryImp){
        this.calenderView = calenderView;
        this.repositry = repositry;
        this.backUpRepositoryImp =backUpRepositoryImp;

    }
    @Override
    public void getMealsFromCalendar(String date) {
        repositry.getAllMealsFromCalendar(date)
                .observe((LifecycleOwner) calenderView, new Observer<List<MealCalendar>>() {
            @Override
            public void onChanged(List<MealCalendar> mealsItems) {
                calenderView.showData(mealsItems);
            }
        });

        Log.i("TAG", "getData: " + UserProfile.getCurrentUserId() );
        backUpRepositoryImp.retrievePlanMeals(UserProfile.getCurrentUserId(), date, new MealPlanCallBack() {

            @Override
            public void onSuccess(List<MealCalendar> mealsItemsList) {
                calenderView.showData(mealsItemsList);
            }

            @Override
            public void onFailure(String error) {
                calenderView.showErrorMsg(error);
            }
        });
    }

    @Override
    public void removeFromCalnder(MealCalendar mealCalendar) {
        repositry.deleteMealFromCalendar(mealCalendar);


        String userId = UserProfile.getCurrentUserId();
        String mealId = mealCalendar.getMealIdInFirabse();
        Log.i("TAG", "removeFromFav: " + mealId);
        if (userId != null && mealId != null) {
            backUpRepositoryImp.deleteMealOfPlan(userId, mealId, new DeleteMealCallback() {
                @Override
                public void onSuccess() {
                    calenderView.afterRemove();
                }
                @Override
                public void onFailure(String error) {
                    calenderView.showErrorMsg(error);
                }
            });
        } else {
            calenderView.showErrorMsg("NO User, Login First");
        }
    }
}
