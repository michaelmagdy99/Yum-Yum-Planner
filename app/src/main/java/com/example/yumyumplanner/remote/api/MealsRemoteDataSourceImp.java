// MealsRemoteDataSourceImp.java
package com.example.yumyumplanner.remote.api;

import com.example.yumyumplanner.model.data.CategoriesResponse;
import com.example.yumyumplanner.model.data.CountryResponse;
import com.example.yumyumplanner.model.data.IngredientsResponse;
import com.example.yumyumplanner.model.data.MealResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImp implements MealsRemoteDataSource {

    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private MealsRemoteWebService mealsRemoteWebService;
    private static MealsRemoteDataSourceImp mealsRemoteDataSource;

    public static synchronized MealsRemoteDataSourceImp getInstance() {
        if (mealsRemoteDataSource == null) {
            mealsRemoteDataSource = new MealsRemoteDataSourceImp();
        }
        return mealsRemoteDataSource;
    }

    private MealsRemoteDataSourceImp() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        mealsRemoteWebService = retrofit.create(MealsRemoteWebService.class);
    }

    @Override
    public void makeRandomMealCall(NetworkCallBack networkCallBack) {
        Single<MealResponse> randomMealCall = mealsRemoteWebService.getRandomMeal();
        randomMealCall.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                                networkCallBack.onSuccessResult(response.meals);

                            }, error ->
                                networkCallBack.onFailureResult("Network request failed. " +
                                        "Error: " + error.getMessage()));
    }

    @Override
    public void makeIngredientsCall(NetworkCallBack networkCallBack) {
        Single<IngredientsResponse> ingredientsCall = mealsRemoteWebService.getIngredients();


        ingredientsCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    networkCallBack.onSuccessResult(response.ingredientItems);

                }, error ->
                        networkCallBack.onFailureResult("Network request failed. " +
                                "Error: " + error.getMessage()));
    }

    @Override
    public void makeCategoriesCall(NetworkCallBack networkCallBack) {
        Single<CategoriesResponse> categoriesCall = mealsRemoteWebService.getCategories();

        categoriesCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    networkCallBack.onSuccessResult(response.getCategories());

                }, error ->
                        networkCallBack.onFailureResult("Network request failed. " +
                                "Error: " + error.getMessage()));
    }

    @Override
    public void makeCountryCall(NetworkCallBack networkCallBack) {
        Single<CountryResponse> countryCall = mealsRemoteWebService.getCuisines();
        countryCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    networkCallBack.onSuccessResult(response.getCountry());

                }, error ->
                        networkCallBack.onFailureResult("Network request failed. " +
                                "Error: " + error.getMessage()));
    }
}
