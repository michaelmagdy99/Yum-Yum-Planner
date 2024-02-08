package com.example.yumyumplanner.home.meal_details.preseter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.yumyumplanner.home.meal_details.view.MealsDetailsView;
import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.data.UserProfile;
import com.example.yumyumplanner.model.meals_repo.FilterRepoImp;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.NetworkCallBack;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;
import com.example.yumyumplanner.remote.firebase.backup.DeleteMealCallback;

import java.util.List;

public class MealDetailsPreserter implements  MealDetailsPresenterInterface, NetworkCallBack {
    private HomeRepositryImp mealsRepositry;

    private FilterRepoImp filterRepo;

    private MealsDetailsView view;
    private BackUpDataSourceImp backUpRepository;

    public MealDetailsPreserter(MealsDetailsView view, HomeRepositryImp mealsRepositry, FilterRepoImp filterRepo, BackUpDataSourceImp backUpRepository){
        this.mealsRepositry = mealsRepositry;
        this.view = view;
        this.filterRepo = filterRepo;
        this.backUpRepository = backUpRepository;

    }
    @Override
    public void addToFav(MealsItem meal) {
        mealsRepositry.insertMeal(meal);
        String userId = UserProfile.getCurrentUserId();
        backUpRepository.uploadMeals(meal, userId);
    }

    @Override
    public void addToCalender(MealCalendar mealCalendar) {
        mealsRepositry.addToCalendar(mealCalendar);
        String userId = UserProfile.getCurrentUserId();
        backUpRepository.uploadPlanMeals(mealCalendar, userId);
    }

    @Override
    public void getMealDetails(String id) {
        filterRepo.getMealsById(this, id);
    }

    @Override
    public void onSuccessResult(Object result) {
        if (result instanceof List<?>) {
            List<?> resultList = (List<?>) result;

            if (!resultList.isEmpty()) {
                Object item = resultList.get(0);

                if (item instanceof MealsItem) {
                    // Display meals data
                    view.showData((List<MealsItem>) result);
                }
                else {
                    view.showEmptyDataMessage();
                }
            } else {
                view.showErrorMsg("Unexpected result type: " + result.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void onFailureResult(String message) {
        view.showErrorMsg(message);

    }

    @Override
    public void removeFromFav(MealsItem mealsItem) {
        String userId = UserProfile.getCurrentUserId();
        String mealId = mealsItem.getIdMeal();
        Log.i("TAG", "removeFromFav: " + mealId);
        if (userId != null && mealId != null) {
            backUpRepository.deleteMeal(userId, mealId, new DeleteMealCallback() {
                @Override
                public void onSuccess() {
                    mealsRepositry.deleteMeal(mealsItem);
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

    @Override
    public LiveData<List<MealsItem>> getMealById(String id) {
        return mealsRepositry.getMealById(id);
    }
}
