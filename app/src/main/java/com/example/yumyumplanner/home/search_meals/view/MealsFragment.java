package com.example.yumyumplanner.home.search_meals.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.home.home.view.HomeFragmentDirections;
import com.example.yumyumplanner.home.home.view.OnClickListener;
import com.example.yumyumplanner.home.ingredient_details.presenter.IngredientsPresenterImp;
import com.example.yumyumplanner.home.ingredient_details.view.MealByIngrAdapter;
import com.example.yumyumplanner.home.search_meals.presenter.MealsPresenterImp;
import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.CountryItem;
import com.example.yumyumplanner.model.data.FilterItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.Item;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.FilterRepoImp;
import com.example.yumyumplanner.remote.api.MealsRemoteDataSourceImp;

import java.util.ArrayList;
import java.util.List;


public class MealsFragment extends Fragment implements OnClickListener, MealsView {

    private View view;

    ImageButton backBtn;
    EditText searchBtn;
    RecyclerView mealsRecyclerView;
    LinearLayoutManager mealLayoutManager;

    private ProgressDialog progressDialog;

    MealByIngrAdapter mealsAdapter;

    String nameOfCountry ,nameOfCatogory;

    MealsPresenterImp mealsPresenters;

    public MealsFragment() {
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
        view = inflater.inflate(R.layout.fragment_meals, container, false);
        initUi(view);
        showProgressBar();

        mealsPresenters =new MealsPresenterImp(
                this,
                FilterRepoImp.getInstance(
                        MealsRemoteDataSourceImp.getInstance()));

        //object of mealRV
        mealLayoutManager = new LinearLayoutManager(getContext());
        mealLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mealsAdapter = new MealByIngrAdapter(getContext(), new ArrayList<>(), this);
        //mealsRV
        mealsRecyclerView.setLayoutManager(mealLayoutManager);
        mealsRecyclerView.setAdapter(mealsAdapter);
        //get by country
        Bundle args = getArguments();
        if (args != null) {
            CountryItem countryItem = MealsFragmentArgs.fromBundle(args).getCountryItem();
            if (countryItem != null) {
                nameOfCountry = countryItem.getStrArea();
                mealsPresenters.getMealsByCountry(nameOfCountry);
            }
            CategoriesItem categoryItem = MealsFragmentArgs.fromBundle(args).getCategoryItem();
            if (categoryItem != null) {
                nameOfCatogory = categoryItem.getStrCategory();
                mealsPresenters.getMealsByCategory(nameOfCatogory);
            }
        }

        searchBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().toLowerCase();
                mealsPresenters.searchFilterItem(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        return view;
    }

    void initUi(View view){
        backBtn = view.findViewById(R.id.back_btn_meal);
        mealsRecyclerView = view.findViewById(R.id.meals_recycler_view_meals);
        searchBtn = view.findViewById(R.id.search_meals);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait... It is downloading");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
    }


    private void showProgressBar() {
        progressDialog.show();
    }

    private void hideProgressBar() {
        progressDialog.dismiss();
    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof FilterItem) {
            FilterItem filterItem = (FilterItem) item;
            MealsFragmentDirections.ActionMealsFragmentToMealDetailsFragment actionMealsFragmentToMealDetailsFragment =
                    MealsFragmentDirections.actionMealsFragmentToMealDetailsFragment();
            actionMealsFragmentToMealDetailsFragment.setFilterItem(filterItem);
            Navigation.findNavController(view).navigate(actionMealsFragmentToMealDetailsFragment);
        }
        else if (item instanceof MealsItem) {
            MealsItem mealsItem = (MealsItem) item;
            MealsFragmentDirections.ActionMealsFragmentToMealDetailsFragment actionMealsFragmentToMealDetailsFragment =
                    MealsFragmentDirections.actionMealsFragmentToMealDetailsFragment();
            actionMealsFragmentToMealDetailsFragment.setMealDetails(mealsItem);
            Navigation.findNavController(view).navigate(actionMealsFragmentToMealDetailsFragment);
        }
    }

    @Override
    public void showMealsFromCountry(List<Item> countries) {
        hideProgressBar();
        if(countries != null){
            mealsAdapter.setList(countries);
            mealsAdapter.notifyDataSetChanged();
        }else{
            mealsAdapter.setList(new ArrayList<>());
            mealsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showMealsFromCategory(List<Item> categories) {
        hideProgressBar();
        if(categories != null){
            mealsAdapter.setList(categories);
            mealsAdapter.notifyDataSetChanged();
        }else{
            mealsAdapter.setList(new ArrayList<>());
            mealsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showEmptyDataMessage() {
        hideProgressBar();
    }

    @Override
    public void showErrorMsg(String error) {
        hideProgressBar();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMeals(List<Item> mealsItems) {
        hideProgressBar();
        if(mealsItems != null){
            mealsAdapter.setList(mealsItems);
            mealsAdapter.notifyDataSetChanged();
        }else{
            mealsAdapter.setList(new ArrayList<>());
            mealsAdapter.notifyDataSetChanged();
        }
    }
}