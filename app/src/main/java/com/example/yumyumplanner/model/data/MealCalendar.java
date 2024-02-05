package com.example.yumyumplanner.model.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "meal_plan")
public class MealCalendar implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("idMeal")
    private String idMeal;
    @ColumnInfo(name = "dayOfWeek")
    public int dayOfWeek;
    @ColumnInfo(name = "date")
    public String date;

    private  String mealIdInFirabse;

    public String getMealIdInFirabse() {
        return mealIdInFirabse;
    }

    public  void setMealIdInFirabse(String mealIdInFirabse) {
        this.mealIdInFirabse = mealIdInFirabse;
    }


    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    public void setStrImageSource(Object strImageSource) {
        this.strImageSource = strImageSource;
    }

    public void setStrIngredient10(String strIngredient10) {
        this.strIngredient10 = strIngredient10;
    }

    public void setStrIngredient12(String strIngredient12) {
        this.strIngredient12 = strIngredient12;
    }

    public void setStrIngredient11(String strIngredient11) {
        this.strIngredient11 = strIngredient11;
    }

    public void setStrIngredient14(String strIngredient14) {
        this.strIngredient14 = strIngredient14;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public void setStrIngredient13(String strIngredient13) {
        this.strIngredient13 = strIngredient13;
    }

    public void setStrIngredient16(String strIngredient16) {
        this.strIngredient16 = strIngredient16;
    }

    public void setStrIngredient15(String strIngredient15) {
        this.strIngredient15 = strIngredient15;
    }

    public void setStrIngredient18(String strIngredient18) {
        this.strIngredient18 = strIngredient18;
    }

    public void setStrIngredient17(String strIngredient17) {
        this.strIngredient17 = strIngredient17;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public void setStrCreativeCommonsConfirmed(Object strCreativeCommonsConfirmed) {
        this.strCreativeCommonsConfirmed = strCreativeCommonsConfirmed;
    }

    public void setStrIngredient19(String strIngredient19) {
        this.strIngredient19 = strIngredient19;
    }

    public void setStrTags(String strTags) {
        this.strTags = strTags;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public void setStrIngredient1(String strIngredient1) {
        this.strIngredient1 = strIngredient1;
    }

    public void setStrIngredient3(String strIngredient3) {
        this.strIngredient3 = strIngredient3;
    }

    public void setStrIngredient2(String strIngredient2) {
        this.strIngredient2 = strIngredient2;
    }

    public void setStrIngredient20(String strIngredient20) {
        this.strIngredient20 = strIngredient20;
    }

    public void setStrIngredient5(String strIngredient5) {
        this.strIngredient5 = strIngredient5;
    }

    public void setStrIngredient4(String strIngredient4) {
        this.strIngredient4 = strIngredient4;
    }

    public void setStrIngredient7(String strIngredient7) {
        this.strIngredient7 = strIngredient7;
    }

    public void setStrIngredient6(String strIngredient6) {
        this.strIngredient6 = strIngredient6;
    }

    public void setStrIngredient9(String strIngredient9) {
        this.strIngredient9 = strIngredient9;
    }

    public void setStrIngredient8(String strIngredient8) {
        this.strIngredient8 = strIngredient8;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public void setStrMeasure20(String strMeasure20) {
        this.strMeasure20 = strMeasure20;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public void setStrMeasure12(String strMeasure12) {
        this.strMeasure12 = strMeasure12;
    }

    public void setStrMeasure13(String strMeasure13) {
        this.strMeasure13 = strMeasure13;
    }

    public void setStrMeasure10(String strMeasure10) {
        this.strMeasure10 = strMeasure10;
    }

    public void setStrMeasure11(String strMeasure11) {
        this.strMeasure11 = strMeasure11;
    }

    public void setDateModified(Object dateModified) {
        this.dateModified = dateModified;
    }

    public void setStrDrinkAlternate(Object strDrinkAlternate) {
        this.strDrinkAlternate = strDrinkAlternate;
    }

    public void setStrSource(String strSource) {
        this.strSource = strSource;
    }

    public void setStrMeasure9(String strMeasure9) {
        this.strMeasure9 = strMeasure9;
    }

    public void setStrMeasure7(String strMeasure7) {
        this.strMeasure7 = strMeasure7;
    }

    public void setStrMeasure8(String strMeasure8) {
        this.strMeasure8 = strMeasure8;
    }

    public void setStrMeasure5(String strMeasure5) {
        this.strMeasure5 = strMeasure5;
    }

    public void setStrMeasure6(String strMeasure6) {
        this.strMeasure6 = strMeasure6;
    }

    public void setStrMeasure3(String strMeasure3) {
        this.strMeasure3 = strMeasure3;
    }

    public void setStrMeasure4(String strMeasure4) {
        this.strMeasure4 = strMeasure4;
    }

    public void setStrMeasure1(String strMeasure1) {
        this.strMeasure1 = strMeasure1;
    }

    public void setStrMeasure18(String strMeasure18) {
        this.strMeasure18 = strMeasure18;
    }

    public void setStrMeasure2(String strMeasure2) {
        this.strMeasure2 = strMeasure2;
    }

    public void setStrMeasure19(String strMeasure19) {
        this.strMeasure19 = strMeasure19;
    }

    public void setStrMeasure16(String strMeasure16) {
        this.strMeasure16 = strMeasure16;
    }

    public void setStrMeasure17(String strMeasure17) {
        this.strMeasure17 = strMeasure17;
    }

    public void setStrMeasure14(String strMeasure14) {
        this.strMeasure14 = strMeasure14;
    }

    public void setStrMeasure15(String strMeasure15) {
        this.strMeasure15 = strMeasure15;
    }

    @Ignore
    @SerializedName("strImageSource")
    private Object strImageSource;
    @ColumnInfo(name = "ingredient_10")
    @SerializedName("strIngredient10")
    private String strIngredient10;
    @ColumnInfo(name = "ingredient_12")
    @SerializedName("strIngredient12")
    private String strIngredient12;
    @ColumnInfo(name = "ingredient_11")
    @SerializedName("strIngredient11")
    private String strIngredient11;
    @ColumnInfo(name = "ingredient_14")
    @SerializedName("strIngredient14")
    private String strIngredient14;
    @ColumnInfo(name = "category")
    @SerializedName("strCategory")
    private String strCategory;
    @ColumnInfo(name = "ingredient_13")
    @SerializedName("strIngredient13")
    private String strIngredient13;
    @ColumnInfo(name = "ingredient_16")
    @SerializedName("strIngredient16")
    private String strIngredient16;
    @ColumnInfo(name = "ingredient_15")
    @SerializedName("strIngredient15")
    private String strIngredient15;
    @ColumnInfo(name = "ingredient_18")
    @SerializedName("strIngredient18")
    private String strIngredient18;
    @ColumnInfo(name = "ingredient_17")
    @SerializedName("strIngredient17")
    private String strIngredient17;
    @ColumnInfo(name = "area")
    @SerializedName("strArea")
    private String strArea;
    @Ignore
    @SerializedName("strCreativeCommonsConfirmed")
    private Object strCreativeCommonsConfirmed;
    @ColumnInfo(name = "ingredient_19")
    @SerializedName("strIngredient19")
    private String strIngredient19;

    @Ignore
    @SerializedName("strTags")
    private String strTags;

    @ColumnInfo(name = "instructions")
    @SerializedName("strInstructions")
    private String strInstructions;
    @ColumnInfo(name = "ingredient_1")
    @SerializedName("strIngredient1")
    private String strIngredient1;
    @ColumnInfo(name = "ingredient_3")
    @SerializedName("strIngredient3")
    private String strIngredient3;
    @ColumnInfo(name = "ingredient_2")
    @SerializedName("strIngredient2")
    private String strIngredient2;
    @ColumnInfo(name = "ingredient_20")
    @SerializedName("strIngredient20")
    private String strIngredient20;
    @ColumnInfo(name = "ingredient_5")
    @SerializedName("strIngredient5")
    private String strIngredient5;
    @ColumnInfo(name = "ingredient_4")
    @SerializedName("strIngredient4")
    private String strIngredient4;
    @ColumnInfo(name = "ingredient_7")
    @SerializedName("strIngredient7")
    private String strIngredient7;
    @ColumnInfo(name = "ingredient_6")
    @SerializedName("strIngredient6")
    private String strIngredient6;
    @ColumnInfo(name = "ingredient_9")
    @SerializedName("strIngredient9")
    private String strIngredient9;

    @ColumnInfo(name = "ingredient_8")
    @SerializedName("strIngredient8")
    private String strIngredient8;

    @ColumnInfo(name = "strMealThumb")
    @SerializedName("strMealThumb")
    private String strMealThumb;

    @ColumnInfo(name = "measure_20")
    @SerializedName("strMeasure20")
    private String strMeasure20;
    @ColumnInfo(name = "youtube")
    @SerializedName("strYoutube")
    private String strYoutube;

    @ColumnInfo(name = "meal_name")
    @SerializedName("strMeal")
    private String strMeal;

    @ColumnInfo(name = "measure_12")
    @SerializedName("strMeasure12")
    private String strMeasure12;

    @ColumnInfo(name = "measure_13")
    @SerializedName("strMeasure13")
    private String strMeasure13;

    @ColumnInfo(name = "measure_10")
    @SerializedName("strMeasure10")
    private String strMeasure10;

    @ColumnInfo(name = "measure_11")
    @SerializedName("strMeasure11")
    private String strMeasure11;
    @Ignore
    @SerializedName("dateModified")
    private Object dateModified;

    @Ignore
    @SerializedName("strDrinkAlternate")
    private Object strDrinkAlternate;

    @Ignore
    @SerializedName("strSource")
    private String strSource;

    @ColumnInfo(name = "measure_9")
    @SerializedName("strMeasure9")
    private String strMeasure9;

    @ColumnInfo(name = "measure_7")
    @SerializedName("strMeasure7")
    private String strMeasure7;

    @ColumnInfo(name = "measure_8")
    @SerializedName("strMeasure8")
    private String strMeasure8;

    @ColumnInfo(name = "measure_5")
    @SerializedName("strMeasure5")
    private String strMeasure5;

    @ColumnInfo(name = "measure_6")
    @SerializedName("strMeasure6")
    private String strMeasure6;

    @ColumnInfo(name = "measure_3")
    @SerializedName("strMeasure3")
    private String strMeasure3;

    @ColumnInfo(name = "measure_4")
    @SerializedName("strMeasure4")
    private String strMeasure4;

    @ColumnInfo(name = "measure_1")
    @SerializedName("strMeasure1")
    private String strMeasure1;

    @ColumnInfo(name = "measure_18")
    @SerializedName("strMeasure18")
    private String strMeasure18;

    @ColumnInfo(name = "measure_2")
    @SerializedName("strMeasure2")
    private String strMeasure2;

    @ColumnInfo(name = "measure_19")
    @SerializedName("strMeasure19")
    private String strMeasure19;

    @ColumnInfo(name = "measure_16")
    @SerializedName("strMeasure16")
    private String strMeasure16;

    @ColumnInfo(name = "measure_17")
    @SerializedName("strMeasure17")
    private String strMeasure17;

    @ColumnInfo(name = "measure_14")
    @SerializedName("strMeasure14")
    private String strMeasure14;

    @ColumnInfo(name = "measure_15")
    @SerializedName("strMeasure15")
    private String strMeasure15;

    public Object getStrImageSource(){
        return strImageSource;
    }

    public String getStrIngredient10(){
        return strIngredient10;
    }

    public String getStrIngredient12(){
        return strIngredient12;
    }

    public String getStrIngredient11(){
        return strIngredient11;
    }

    public String getStrIngredient14(){
        return strIngredient14;
    }

    public String getStrCategory(){
        return strCategory;
    }

    public String getStrIngredient13(){
        return strIngredient13;
    }

    public String getStrIngredient16(){
        return strIngredient16;
    }

    public String getStrIngredient15(){
        return strIngredient15;
    }

    public String getStrIngredient18(){
        return strIngredient18;
    }

    public String getStrIngredient17(){
        return strIngredient17;
    }

    public String getStrArea(){
        return strArea;
    }

    public Object getStrCreativeCommonsConfirmed(){
        return strCreativeCommonsConfirmed;
    }

    public String getStrIngredient19(){
        return strIngredient19;
    }

    public String getStrTags(){
        return strTags;
    }

    public String getIdMeal(){
        return idMeal;
    }

    public String getStrInstructions(){
        return strInstructions;
    }

    public String getStrIngredient1(){
        return strIngredient1;
    }

    public String getStrIngredient3(){
        return strIngredient3;
    }

    public String getStrIngredient2(){
        return strIngredient2;
    }

    public String getStrIngredient20(){
        return strIngredient20;
    }

    public String getStrIngredient5(){
        return strIngredient5;
    }

    public String getStrIngredient4(){
        return strIngredient4;
    }

    public String getStrIngredient7(){
        return strIngredient7;
    }

    public String getStrIngredient6(){
        return strIngredient6;
    }

    public String getStrIngredient9(){
        return strIngredient9;
    }

    public String getStrIngredient8(){
        return strIngredient8;
    }

    public String getStrMealThumb(){
        return strMealThumb;
    }

    public String getStrMeasure20(){
        return strMeasure20;
    }

    public String getStrYoutube(){
        return strYoutube;
    }

    public String getStrMeal(){
        return strMeal;
    }

    public String getStrMeasure12(){
        return strMeasure12;
    }

    public String getStrMeasure13(){
        return strMeasure13;
    }

    public String getStrMeasure10(){
        return strMeasure10;
    }

    public String getStrMeasure11(){
        return strMeasure11;
    }

    public Object getDateModified(){
        return dateModified;
    }

    public Object getStrDrinkAlternate(){
        return strDrinkAlternate;
    }

    public String getStrSource(){
        return strSource;
    }

    public String getStrMeasure9(){
        return strMeasure9;
    }

    public String getStrMeasure7(){
        return strMeasure7;
    }

    public String getStrMeasure8(){
        return strMeasure8;
    }

    public String getStrMeasure5(){
        return strMeasure5;
    }

    public String getStrMeasure6(){
        return strMeasure6;
    }

    public String getStrMeasure3(){
        return strMeasure3;
    }

    public String getStrMeasure4(){
        return strMeasure4;
    }

    public String getStrMeasure1(){
        return strMeasure1;
    }

    public String getStrMeasure18(){
        return strMeasure18;
    }

    public String getStrMeasure2(){
        return strMeasure2;
    }

    public String getStrMeasure19(){
        return strMeasure19;
    }

    public String getStrMeasure16(){
        return strMeasure16;
    }

    public String getStrMeasure17(){
        return strMeasure17;
    }

    public String getStrMeasure14(){
        return strMeasure14;
    }

    public String getStrMeasure15(){
        return strMeasure15;
    }

    public List<String> getAllIngredients() {
        List<String> ingredientsList = new ArrayList<>();

        // get all ingredients
        for (int i = 1; i <= 20; i++) {
            String ingredient = getIngredient(i);
            if (ingredient != null && !ingredient.isEmpty()) {
                ingredientsList.add(ingredient);
            }
        }
        return ingredientsList;
    }

    public List<String> getAllMeaurse() {
        List<String> measureList = new ArrayList<>();

        // get all measures
        for (int i = 1; i <= 20; i++) {
            String measure = getMeasure(i);
            if (measure != null && !measure.isEmpty()) {
                measureList.add(measure);
            }
        }
        return measureList;
    }

    private String getIngredient(int index) {
        switch (index) {
            case 1: return strIngredient1;
            case 2: return strIngredient2;
            case 3: return strIngredient3;
            case 4: return strIngredient4;
            case 5: return strIngredient5;
            case 6: return strIngredient6;
            case 7: return strIngredient7;
            case 8: return strIngredient8;
            case 9: return strIngredient9;
            case 10: return strIngredient10;
            case 11: return strIngredient11;
            case 12: return strIngredient12;
            case 13: return strIngredient13;
            case 14: return strIngredient14;
            case 15: return strIngredient15;
            case 16: return strIngredient16;
            case 17: return strIngredient17;
            case 18: return strIngredient18;
            case 19: return strIngredient19;
            case 20: return strIngredient20;
            default: return null;
        }
    }

    private String getMeasure(int index) {
        switch (index) {
            case 1: return strMeasure1;
            case 2: return strMeasure2;
            case 3: return strMeasure3;
            case 4: return strMeasure4;
            case 5: return strMeasure5;
            case 6: return strMeasure6;
            case 7: return strMeasure7;
            case 8: return strMeasure8;
            case 9: return strMeasure9;
            case 10: return strMeasure10;
            case 11: return strMeasure11;
            case 12: return strMeasure12;
            case 13: return strMeasure13;
            case 14: return strMeasure14;
            case 15: return strMeasure15;
            case 16: return strMeasure16;
            case 17: return strMeasure17;
            case 18: return strMeasure18;
            case 19: return strMeasure19;
            case 20: return strMeasure20;
            default: return null;
        }
    }

    public void setAllIngredients(List<String> ingredientsList) {
        // set all ingredients
        for (int i = 0; i < ingredientsList.size() && i < 20; i++) {
            setIngredient(i + 1, ingredientsList.get(i));
        }
    }

    public void setAllMeasures(List<String> measureList) {
        // set all measures
        for (int i = 0; i < measureList.size() && i < 20; i++) {
            setMeasure(i + 1, measureList.get(i));
        }
    }
    private void setIngredient(int index, String ingredient) {
        switch (index) {
            case 1: strIngredient1 = ingredient; break;
            case 2: strIngredient2 = ingredient; break;
            case 3: strIngredient3 = ingredient; break;
            case 4: strIngredient4 = ingredient; break;
            case 5: strIngredient5 = ingredient; break;
            case 6: strIngredient6 = ingredient; break;
            case 7: strIngredient7 = ingredient; break;
            case 8: strIngredient8 = ingredient; break;
            case 9: strIngredient9 = ingredient; break;
            case 10: strIngredient10 = ingredient; break;
            case 11: strIngredient11 = ingredient; break;
            case 12: strIngredient12 = ingredient; break;
            case 13: strIngredient13 = ingredient; break;
            case 14: strIngredient14 = ingredient; break;
            case 15: strIngredient15 = ingredient; break;
            case 16: strIngredient16 = ingredient; break;
            case 17: strIngredient17 = ingredient; break;
            case 18: strIngredient18 = ingredient; break;
            case 19: strIngredient19 = ingredient; break;
            case 20: strIngredient20 = ingredient; break;
        }
    }

    private void setMeasure(int index, String measure) {
        switch (index) {
            case 1: strMeasure1 = measure; break;
            case 2: strMeasure2 = measure; break;
            case 3: strMeasure3 = measure; break;
            case 4: strMeasure4 = measure; break;
            case 5: strMeasure5 = measure; break;
            case 6: strMeasure6 = measure; break;
            case 7: strMeasure7 = measure; break;
            case 8: strMeasure8 = measure; break;
            case 9: strMeasure9 = measure; break;
            case 10: strMeasure10 = measure; break;
            case 11: strMeasure11 = measure; break;
            case 12: strMeasure12 = measure; break;
            case 13: strMeasure13 = measure; break;
            case 14: strMeasure14 = measure; break;
            case 15: strMeasure15 = measure; break;
            case 16: strMeasure16 = measure; break;
            case 17: strMeasure17 = measure; break;
            case 18: strMeasure18 = measure; break;
            case 19: strMeasure19 = measure; break;
            case 20: strMeasure20 = measure; break;
        }
    }

}
