package com.example.yumyumplanner.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FilterItem implements Serializable, Item {

	@SerializedName("strMealThumb")
	private String strMealThumb;

	@SerializedName("idMeal")
	private String idMeal;

	@SerializedName("strMeal")
	private String strMeal;

	public String getStrMealThumb(){
		return strMealThumb;
	}

	public String getIdMeal(){
		return idMeal;
	}

	public String getStrMeal(){
		return strMeal;
	}

	@Override
	public String getName() {
		return strMeal;
	}

	@Override
	public String getImageUrl() {
		return strMealThumb;
	}
}