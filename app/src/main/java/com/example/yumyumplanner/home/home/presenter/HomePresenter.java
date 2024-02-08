package com.example.yumyumplanner.home.home.presenter;

import android.util.Log;

import com.example.yumyumplanner.home.home.view.HomeView;
import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.CountryItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.data.UserProfile;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.NetworkCallBack;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;
import com.example.yumyumplanner.remote.firebase.backup.DeleteMealCallback;

import java.util.List;

public class HomePresenter implements HomePresenterInterface, NetworkCallBack {

    private HomeView homeView;
    private HomeRepositryImp mealsRepositry;
    private BackUpDataSourceImp backUpRepository;
    public HomePresenter(HomeView homeView, HomeRepositryImp mealsRepositry, BackUpDataSourceImp backUpRepository){
        this.homeView = homeView;
        this.mealsRepositry = mealsRepositry;
        this.backUpRepository = backUpRepository;
    }

    @Override
    public void getMeals() {
        mealsRepositry.getRandomMeal(this);
    }

    @Override
    public void addToFav(MealsItem meal) {
        mealsRepositry.insertMeal(meal);
        String userId = UserProfile.getCurrentUserId();
        backUpRepository.uploadMeals(meal, userId);
    }

    @Override
    public void getIngredients() {
        mealsRepositry.getIngredients(this);
    }

    @Override
    public void getCategories() {
        mealsRepositry.getCategories(this);
    }

    @Override
    public void getCountry() {
        mealsRepositry.getCountry(this);
    }

    @Override
    public void onSuccessResult(Object result) {
        if (result instanceof List<?>) {
            List<?> resultList = (List<?>) result;

            if (!resultList.isEmpty()) {
                Object item = resultList.get(0);

                if (item instanceof MealsItem) {
                    // Display meals data
                    homeView.showData((List<MealsItem>) result);
                } else if (item instanceof IngredientItem) {
                    // Display ingredients data
                    homeView.showIngredients((List<IngredientItem>) result);
                }
                else if (item instanceof CategoriesItem) {
                    // Display CategoriesItem data
                    homeView.showCategory((List<CategoriesItem>) result);
            } else if (item instanceof CountryItem) {
                    // Display CategoriesItem data
                    homeView.showCountry((List<CountryItem>) result);
                }
                else {
                homeView.showEmptyDataMessage();
            }
        } else {
                homeView.showErrorMsg("Unexpected result type: " + result.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void onFailureResult(String message) {
        homeView.showErrorMsg(message);
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
                    homeView.showErrorMsg(error);
                }
            });
        } else {
            homeView.showErrorMsg("NO User, Login First");
        }
    }
}
