package com.example.yumyumplanner.home.home.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yumyumplanner.R;
import com.example.yumyumplanner.home.home.presenter.HomePresenter;
import com.example.yumyumplanner.home.meal_details.IngrdientsAdapter;
import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.network.MealsRemoteDataSourceImp;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView, OnClickListener{


    private View view;
    private TextView nameMeal;
    private HomePresenter homepresenter;
    private TextView countryMeal;
    private ImageView mealImage;
    private TextView categoryMeal;
    private ConstraintLayout itemConstraint;
    public static final String TAG = "Home Fragment";

    private IngrdientsHomeAdapter ingtrdientsHomeAdapter;
    private RecyclerView ingradientRecyclerView;
    LinearLayoutManager IngrlayoutManager;
    LinearLayoutManager caterlayoutManager;

    private CategoryHomeAdapter categoryHomeAdapter;
    private RecyclerView categoryRecyclerView;

    private ProgressDialog progressDialog;

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
        showProgressBar();
        //object of ingtrdientsHomeAdapter
        IngrlayoutManager = new LinearLayoutManager(getContext());
        IngrlayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ingtrdientsHomeAdapter = new IngrdientsHomeAdapter(getContext(), new ArrayList<>(),this);
        //object of categoryHomeAdapter
        caterlayoutManager = new LinearLayoutManager(getContext());
        caterlayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryHomeAdapter = new CategoryHomeAdapter(getContext(), new ArrayList<>(), this);
        homepresenter =new HomePresenter(this,
                HomeRepositryImp.getInstance(
                MealsRemoteDataSourceImp.getInstance()));
        //ingradientsRV
        ingradientRecyclerView.setLayoutManager(IngrlayoutManager);
        ingradientRecyclerView.setAdapter(ingtrdientsHomeAdapter);
        //categoryRV
        categoryRecyclerView.setLayoutManager(caterlayoutManager);
        categoryRecyclerView.setAdapter(categoryHomeAdapter);
        //radom meal
        homepresenter.getMeals();
        //ingradient
        homepresenter.getIngredients();
        //categoty
        homepresenter.getCategories();

        return view;
    }
    private void intitUI(View view){
        mealImage = view.findViewById(R.id.image_meal);
        nameMeal = view.findViewById(R.id.name_meal);
        countryMeal = view.findViewById(R.id.country_meal);
        categoryMeal = view.findViewById(R.id.category_meal);
        itemConstraint = view.findViewById(R.id.item_constrian);
        ingradientRecyclerView = view.findViewById(R.id.ingredients_recycler_home);
        categoryRecyclerView = view.findViewById(R.id.category_recycler_home);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait... It is downloading");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

    }

    @Override
    public void showData(List<MealsItem> mealsItems) {
        hideProgressBar();

        Glide.with(getContext())
                .load(mealsItems.get(0).getStrMealThumb())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(mealImage);

        Log.i(TAG, "showData: " + mealsItems.get(0).getStrYoutube());
        nameMeal.setText(mealsItems.get(0).getStrMeal());
        countryMeal.setText(mealsItems.get(0).getStrArea());
        categoryMeal.setText(mealsItems.get(0).getStrCategory());
        itemConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("mealName", mealsItems.get(0).getStrMeal());
                bundle.putString("categoryName", mealsItems.get(0).getStrCategory());
                bundle.putString("countryMeal", mealsItems.get(0).getStrArea());
                bundle.putString("instructionMeal", mealsItems.get(0).getStrInstructions());
                bundle.putString("imageMeal", mealsItems.get(0).getStrMealThumb());
                bundle.putString("videoMeal",mealsItems.get(0).getStrYoutube());
                bundle.putStringArrayList("ingredientsList", new ArrayList<>(mealsItems.get(0).getAllIngredients()));
                bundle.putStringArrayList("measureList", new ArrayList<>(mealsItems.get(0).getAllMeaurse()));


                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_mealDetailsFragment, bundle);
            }
        });
    }

    @Override
    public void showIngredients(List<IngredientItem> ingredientItems) {
        hideProgressBar();

        Log.i(TAG, "showDataIngr: " + ingredientItems.get(0).getStrIngredient());
        ingtrdientsHomeAdapter.setList(ingredientItems);
        ingtrdientsHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategory(List<CategoriesItem> categoriesItems) {
        Log.i(TAG, "showDataCateg: " + categoriesItems.get(0).getStrCategory());
        hideProgressBar();

        categoryHomeAdapter.setList(categoriesItems);
        categoryHomeAdapter.notifyDataSetChanged();

    }

    @Override
    public void showErrorMsg(String error) {
        hideProgressBar();

        Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addMeal(MealsItem mealsItem) {

    }

    @Override
    public void showEmptyDataMessage() {
        hideProgressBar();

    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof CategoriesItem) {
            CategoriesItem categoriesItem = (CategoriesItem) item;
            //logic
            Toast.makeText(getContext(), categoriesItem.getStrCategory(), Toast.LENGTH_SHORT).show();
        } else if (item instanceof IngredientItem) {
            IngredientItem ingredientItem = (IngredientItem) item;
            //logic
            Toast.makeText(getContext(), ingredientItem.getStrIngredient(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgressBar() {
        progressDialog.show();
    }

    private void hideProgressBar() {
        progressDialog.dismiss();
    }
}