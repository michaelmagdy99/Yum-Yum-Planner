package com.example.yumyumplanner.model.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CountryResponse{

	@SerializedName("meals")
	private List<CountryItem> country;

	public List<CountryItem> getCountry(){
		return country;
	}
}