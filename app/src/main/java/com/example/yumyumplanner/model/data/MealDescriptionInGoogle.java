package com.example.yumyumplanner.model.data;

public class MealDescriptionInGoogle {
    public static String getMealString(MealCalendar mealCalendar)
    {
        String str="";
        str = mealCalendar.getStrMeal()+"\n\n"+"Category : "+mealCalendar.getStrCategory()+"\n\n"+"Country : "+mealCalendar.getStrArea()+"\n\n"+
                "Instructions : "+"\n"+formatText(mealCalendar.getStrInstructions())+"\n\n"+"youtube Link : "+mealCalendar.getStrYoutube();
        return str;
    }

    private static String formatText(String strInstructions) {
        strInstructions = strInstructions.replace(". ", ".\n\n");
        return strInstructions;
    }
}
