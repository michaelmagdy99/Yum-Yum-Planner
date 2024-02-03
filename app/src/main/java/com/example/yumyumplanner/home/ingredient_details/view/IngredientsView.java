package com.example.yumyumplanner.home.ingredient_details.view;

import com.example.yumyumplanner.model.data.FilterItem;

import java.util.List;

public interface IngredientsView {
    void showMealsFromIngredints(List<FilterItem> result);
    void showEmptyDataMessage();
    void showErrorMsg(String error);


}
