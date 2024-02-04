package com.example.yumyumplanner.home.ingredient_details.view;

import com.example.yumyumplanner.model.data.FilterItem;
import com.example.yumyumplanner.model.data.Item;

import java.util.List;

public interface IngredientsView {
    void showMealsFromIngredints(List<Item> result);
    void showEmptyDataMessage();
    void showErrorMsg(String error);


}
