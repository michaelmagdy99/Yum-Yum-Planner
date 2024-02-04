package com.example.yumyumplanner.home.search.presenter;

import com.example.yumyumplanner.home.search.view.SearchView;
import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.CountryItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.NetworkCallBack;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

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

    @Override
    public void searchByCategoryText(String searchText) {
        mealsRepositry.getCategories(new NetworkCallBack() {
            @Override
            public void onSuccessResult(Object result) {
                if (result instanceof List<?>) {
                    List<?> resultList = (List<?>) result;

                    if (!resultList.isEmpty()) {
                        Object item = resultList.get(0);

                        if (item instanceof CategoriesItem) {
                            Observable.fromIterable((List<CategoriesItem>) resultList)
                                    .filter(categoryItem -> {
                                        // Filter by category name
                                        String categoryName = ((CategoriesItem) categoryItem).getStrCategory().toLowerCase();
                                        return categoryName.contains(searchText);
                                    })
                                    .toList()
                                    .subscribe(
                                            newList -> {
                                                if (!newList.isEmpty()) {
                                                    searchView.showCategory(newList);
                                                } else {
                                                    searchView.showEmptyDataMessage();
                                                }
                                            }
                                            );
                            }
                            else {
                                searchView.showEmptyDataMessage();
                        }
                    } else {
                        searchView.showEmptyDataMessage();
                    }
                } else {
                    searchView.showErrorMsg("Unexpected result type: " + result.getClass().getSimpleName());
                }
            }

            @Override
            public void onFailureResult(String message) {
                searchView.showErrorMsg(message);
            }
        });
    }

    @Override
    public void searchByCountryText(String searchText) {
        mealsRepositry.getCountry(new NetworkCallBack() {
            @Override
            public void onSuccessResult(Object result) {
                if (result instanceof List<?>) {
                    List<?> resultList = (List<?>) result;

                    if (!resultList.isEmpty()) {
                        Object item = resultList.get(0);

                         if(item instanceof CountryItem){

                            Observable.fromIterable((List<CountryItem>) resultList)
                                    .filter(countryItem -> {
                                        // Filter by country name
                                        String countryName = ((CountryItem) countryItem).getStrArea().toLowerCase();
                                        return countryName.contains(searchText);
                                    })
                                    .toList()
                                    .subscribe(
                                            newList -> {
                                                if (!newList.isEmpty()) {
                                                    searchView.showCounrty(newList);
                                                } else {
                                                    searchView.showEmptyDataMessage();
                                                }
                                            }
                                    );
                    }
                        else {
                            searchView.showEmptyDataMessage();
                        }
                    } else {
                        searchView.showEmptyDataMessage();
                    }
                } else {
                    searchView.showErrorMsg("Unexpected result type: " + result.getClass().getSimpleName());
                }
            }

            @Override
            public void onFailureResult(String message) {
                searchView.showErrorMsg(message);
            }
        });

    }

    @Override
    public void searchByIngrText(String searchText) {
        mealsRepositry.getIngredients(new NetworkCallBack() {
            @Override
            public void onSuccessResult(Object result) {
                if (result instanceof List<?>) {
                    List<?> resultList = (List<?>) result;

                    if (!resultList.isEmpty()) {
                        Object item = resultList.get(0);

                        if(item instanceof IngredientItem){
                            Observable.fromIterable((List<IngredientItem>) resultList)
                                    .filter(ingrItem -> {
                                        // Filter by country name
                                        String ingrName = ((IngredientItem) ingrItem).getStrIngredient().toLowerCase();
                                        return ingrName.contains(searchText);
                                    })
                                    .toList()
                                    .subscribe(
                                            newList -> {
                                                if (!newList.isEmpty()) {
                                                    searchView.showIngredients(newList);
                                                } else {
                                                    searchView.showEmptyDataMessage();
                                                }
                                            }
                                    );
                        }

                        else {
                            searchView.showEmptyDataMessage();
                        }
                    } else {
                        searchView.showEmptyDataMessage();
                    }
                } else {
                    searchView.showErrorMsg("Unexpected result type: " + result.getClass().getSimpleName());
                }
            }

            @Override
            public void onFailureResult(String message) {
                searchView.showErrorMsg(message);
            }
        });

    }


}
