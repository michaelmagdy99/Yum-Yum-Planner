package com.example.yumyumplanner.model.data;

import com.google.gson.annotations.SerializedName;

public class CountryItem{

	@SerializedName("strArea")
	private String strArea;

	public String getStrArea(){
		return strArea;
	}
}