package com.example.yumyumplanner.model.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoriesResponse {

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	public List<CategoriesItem> getCategories(){
		return categories;
	}
}