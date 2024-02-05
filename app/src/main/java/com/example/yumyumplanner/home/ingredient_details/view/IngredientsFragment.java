package com.example.yumyumplanner.home.ingredient_details.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yumyumplanner.R;
import com.example.yumyumplanner.database.MealsLocalDataSourceImp;
import com.example.yumyumplanner.home.home.presenter.HomePresenter;
import com.example.yumyumplanner.home.home.view.CategoryHomeAdapter;
import com.example.yumyumplanner.home.home.view.OnClickListener;
import com.example.yumyumplanner.home.ingredient_details.presenter.IngredientsPresenterImp;
import com.example.yumyumplanner.home.search_meals.view.MealsFragmentDirections;
import com.example.yumyumplanner.model.data.FilterItem;
import com.example.yumyumplanner.model.data.Item;
import com.example.yumyumplanner.model.meals_repo.FilterRepoImp;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.MealsRemoteDataSourceImp;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment implements IngredientsView, OnClickListener {


    private View view;

    private ProgressDialog progressDialog;

    ImageView imageIngredient ;
    TextView nameIngredients;
    TextView description;
    ImageButton backBtn;

    RecyclerView mealsRecyclerView;

    IngredientsPresenterImp ingredientsPresenter;

    GridLayoutManager gridLayoutManager;
    MealByIngrAdapter mealsAdapter;

    String nameOfIngr ,descriptionOfIngr ,idOfIngr;

    public IngredientsFragment() {
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
        view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        initUi(view);
        showProgressBar();
        ingredientsPresenter =new IngredientsPresenterImp(
                this,
                FilterRepoImp.getInstance(
                        MealsRemoteDataSourceImp.getInstance()));

        nameOfIngr = IngredientsFragmentArgs.fromBundle(getArguments()).getIngredientItem().getStrIngredient();
        descriptionOfIngr = IngredientsFragmentArgs.fromBundle(getArguments()).getIngredientItem().getStrDescription();
        idOfIngr = IngredientsFragmentArgs.fromBundle(getArguments()).getIngredientItem().getIdIngredient();

        //object of mealRV
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mealsAdapter = new MealByIngrAdapter(getContext(), new ArrayList<>(), this);
        //mealsRV
        mealsRecyclerView.setLayoutManager(gridLayoutManager);
        mealsRecyclerView.setAdapter(mealsAdapter);

        //set args
        nameIngredients.setText(nameOfIngr);
        description.setText(descriptionOfIngr);

        String imageUrl = "https://www.themealdb.com/images/ingredients/" + nameOfIngr + ".png";
        Glide.with(getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageIngredient);
        //getList
        ingredientsPresenter.getMealsByIngredints(nameOfIngr);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        return view;
    }
    void initUi(View view){
        mealsRecyclerView = view.findViewById(R.id.meals_recycler_view_ingr);
        backBtn = view.findViewById(R.id.back_btn_ingr);
        nameIngredients = view.findViewById(R.id.name_ingr);
        description = view.findViewById(R.id.description);
        imageIngredient =view.findViewById(R.id.image_meal_ingr);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait... It is downloading");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
    }

    @Override
    public void showMealsFromIngredints(List<Item> result) {
        hideProgressBar();
        mealsAdapter.setList(result);
        mealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyDataMessage() {
        hideProgressBar();
    }

    @Override
    public void showErrorMsg(String error) {
        hideProgressBar();
        Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof FilterItem) {
            FilterItem filterItem = (FilterItem) item;
            IngredientsFragmentDirections.ActionIngredientsFragmentToMealDetailsFragment actionMealsFragmentToMealDetailsFragment =
                    IngredientsFragmentDirections.actionIngredientsFragmentToMealDetailsFragment();
            actionMealsFragmentToMealDetailsFragment.setFilterItem(filterItem);
            Navigation.findNavController(view).navigate(actionMealsFragmentToMealDetailsFragment);
        }
    }

    private void showProgressBar() {
        progressDialog.show();
    }

    private void hideProgressBar() {
        progressDialog.dismiss();
    }
}