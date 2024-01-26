package com.example.yumyumplanner.model.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class IngredientsResponse{

	@SerializedName("meals")
	public List<IngredientItem> ingredientItems;

	public List<IngredientItem> getMeals(){
		return ingredientItems;
	}
}