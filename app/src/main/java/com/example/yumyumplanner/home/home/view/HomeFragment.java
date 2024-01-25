package com.example.yumyumplanner.home.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yumyumplanner.R;
import com.example.yumyumplanner.home.home.presenter.HomePresenter;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.MealsRepositryImp;
import com.example.yumyumplanner.network.MealsRemoteDataSource;
import com.example.yumyumplanner.network.MealsRemoteDataSourceImp;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView{


    private View view;
    private TextView nameMeal;
    private HomePresenter homepresenter;
    private TextView countryMeal;
    private ImageView mealImage;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        intitUI(view);

        homepresenter =new HomePresenter(this,
                MealsRepositryImp.getInstance(
                MealsRemoteDataSourceImp.getInstance()));
        homepresenter.getMeals();
        return view;
    }
    private void intitUI(View view){
        mealImage = view.findViewById(R.id.image_meal);
        nameMeal = view.findViewById(R.id.name_meal);
        countryMeal = view.findViewById(R.id.country_meal);
    }

    @Override
    public void showData(List<MealsItem> mealsItems) {
        Glide.with(getContext())
                .load(mealsItems.get(0).getStrMealThumb())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(mealImage);

        nameMeal.setText(mealsItems.get(0).getStrMeal());
        countryMeal.setText(mealsItems.get(0).getStrArea());
    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addMeal(MealsItem mealsItem) {

    }
}