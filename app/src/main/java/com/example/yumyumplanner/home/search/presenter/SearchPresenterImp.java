package com.example.yumyumplanner.home.search.presenter;

import com.example.yumyumplanner.home.search.view.SearchView;
import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.CountryItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.NetworkCallBack;

import java.util.List;

public class SearchPresenterImp implements SearchPresenter, NetworkCallBack {
    private HomeRepositryImp mealsRepositry;

    private SearchView searchView;

    public SearchPresenterImp(SearchView searchView, HomeRepositryImp mealsRepositry){
        this.searchView = searchView;
        this.mealsRepositry = mealsRepositry;
    }


    @Override
    public void addToFav(MealsItem meal) {
        mealsRepositry.insertMeal(meal);
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

                 if (item instanceof IngredientItem) {
                    // Display ingredients data
                    searchView.showIngredients((List<IngredientItem>) result);
                }
                else if (item instanceof CategoriesItem) {
                    // Display CategoriesItem data
                    searchView.showCategory((List<CategoriesItem>) result);

                } else if (item instanceof CountryItem) {
                     // Display CountryItem data
                     searchView.showCounrty((List<CountryItem>) result);
                 }
                else {
                    searchView.showEmptyDataMessage();
                }
            } else {
                searchView.showErrorMsg("Unexpected result type: " + result.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void onFailureResult(String message) {
        searchView.showErrorMsg(message);
    }
}
