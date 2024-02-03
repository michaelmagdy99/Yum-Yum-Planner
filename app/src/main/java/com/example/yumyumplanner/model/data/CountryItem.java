package com.example.yumyumplanner.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CountryItem implements Serializable {

	@SerializedName("strArea")
	private String strArea;

	public String getStrArea(){
		return strArea;
	}

	public static String getUrlForCountry(String countryName) {
		switch (countryName) {
			case "American":
				return "us";
			case "British":
				return "gb";
			case "Canadian":
				return "ca";
			case "Chinese":
				return "cn";
			case "Croatian":
				return "hr";
			case "Dutch":
				return "nl";
			case "Egyptian":
				return "eg";
			case "Filipino":
				return "ph";
			case "French":
				return "fr";
			case "Greek":
				return "gr";
			case "Indian":
				return "in";
			case "Irish":
				return "ie";
			case "Italian":
				return "it";
			case "Jamaican":
				return "jm";
			case "Japanese":
				return "jp";
			case "Kenyan":
				return "ke";
			case "Malaysian":
				return "my";
			case "Mexican":
				return "mx";
			case "Moroccan":
				return "ma";
			case "Polish":
				return "pl";
			case "Portuguese":
				return "pt";
			case "Russian":
				return "ru";
			case "Spanish":
				return "es";
			case "Thai":
				return "th";
			case "Tunisian":
				return "tn";
			case "Turkish":
				return "tr";
			case "Vietnamese":
				return "vn";
			default:
				return "";
		}
	}
}