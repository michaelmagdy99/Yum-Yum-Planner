package com.example.yumyumplanner.model.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FilterMealResponse{

	@SerializedName("meals")
	private List<FilterItem> filter;

	public List<FilterItem> getFilter(){
		return filter;
	}
}