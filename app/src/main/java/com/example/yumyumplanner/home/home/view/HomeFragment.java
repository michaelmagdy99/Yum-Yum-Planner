package com.example.yumyumplanner.home.home.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.yumyumplanner.R;
import com.example.yumyumplanner.authentication.AuthenticationActivity;
import com.example.yumyumplanner.database.MealsLocalDataSourceImp;
import com.example.yumyumplanner.home.HomeActivity;
import com.example.yumyumplanner.home.home.presenter.HomePresenter;
import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.CountryItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.MealsRemoteDataSourceImp;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView, OnClickListener{

    private Boolean favFalg = false;
    private View view;
    private TextView nameMeal;
    private HomePresenter homepresenter;
    private TextView countryMeal;
    private ImageView mealImage;
    private TextView categoryMeal;
    private List<MealsItem> mealsItems;
    private ConstraintLayout itemConstraint;
    public static final String TAG = "Home Fragment";

    private IngrdientsHomeAdapter ingtrdientsHomeAdapter;
    private RecyclerView ingradientRecyclerView;
    LinearLayoutManager IngrlayoutManager;
    GridLayoutManager caterlayoutManager;

    private CategoryHomeAdapter categoryHomeAdapter;
    private RecyclerView categoryRecyclerView;

    private ProgressDialog progressDialog;

    private ImageView favBtn;
    private CountriesHomeAdapter countriesHomeAdapter;

    private LinearLayoutManager countryLayoutManager;
    private RecyclerView countryRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgressBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        intitUI(view);
        //object of presenter
        homepresenter =new HomePresenter(
                this,
                HomeRepositryImp.getInstance(
                        MealsRemoteDataSourceImp.getInstance(),
                        MealsLocalDataSourceImp.getInstance(getContext())),
                BackUpDataSourceImp.getInstance(getContext())
        );


        //object of country
        countryLayoutManager = new LinearLayoutManager(getContext());
        countryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        countriesHomeAdapter = new CountriesHomeAdapter(getContext(), new ArrayList<>(),this);

        //object of ingtrdientsHomeAdapter
        IngrlayoutManager = new LinearLayoutManager(getContext());
        IngrlayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ingtrdientsHomeAdapter = new IngrdientsHomeAdapter(getContext(), new ArrayList<>(),this);
        //object of categoryHomeAdapter
        caterlayoutManager = new GridLayoutManager(getContext(), 2);
        caterlayoutManager.setOrientation(RecyclerView.VERTICAL);
        categoryHomeAdapter = new CategoryHomeAdapter(getContext(), new ArrayList<>(), this);

        //ingradientsRV
        ingradientRecyclerView.setLayoutManager(IngrlayoutManager);
        ingradientRecyclerView.setAdapter(ingtrdientsHomeAdapter);
        //categoryRV
        categoryRecyclerView.setLayoutManager(caterlayoutManager);
        categoryRecyclerView.setAdapter(categoryHomeAdapter);
        //countrysRV
        countryRecyclerView.setLayoutManager(countryLayoutManager);
        countryRecyclerView.setAdapter(countriesHomeAdapter);
        //radom meal
        homepresenter.getMeals();
        //ingradient
        homepresenter.getIngredients();
        //categoty
        homepresenter.getCategories();

        //country
        homepresenter.getCountry();


        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HomeActivity.isGuestMode) {
                    showGuestModeMessage();
                }else{
                if (favFalg) {
                    deleteMeals(mealsItems.get(0));
                    favBtn.setImageDrawable(getResources().getDrawable(R.drawable.faviourte));
                } else {
                    favBtn.setImageDrawable(getResources().getDrawable(R.drawable.fav));
                    addMeal(mealsItems.get(0));
                }
                favFalg = !favFalg;
            }
            }
        });

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
        favBtn = view.findViewById(R.id.fav_btn_home);
        countryRecyclerView = view.findViewById(R.id.country_recycler_home);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait... It is downloading");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

    }

    @Override
    public void showData(List<MealsItem> mealsItems) {
        hideProgressBar();
        this.mealsItems = mealsItems;

        Glide.with(getContext())
                .load(mealsItems.get(0).getStrMealThumb())
                .centerCrop()
                .placeholder(R.drawable.cooking)
                .into(mealImage);

        Log.i(TAG, "showData: " + mealsItems.get(0).getStrYoutube());
        nameMeal.setText(mealsItems.get(0).getStrMeal());
        countryMeal.setText(mealsItems.get(0).getStrArea());
        categoryMeal.setText(mealsItems.get(0).getStrCategory());

        itemConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                        HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment();
                action.setMealDetails(mealsItems.get(0));
                Navigation.findNavController(view).navigate(action);
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
        hideProgressBar();
        categoryHomeAdapter.setList(categoriesItems);
        categoryHomeAdapter.notifyDataSetChanged();

    }

    @Override
    public void showCountry(List<CountryItem> countryItems) {
        Log.i("TAG", "showDataCateg: " + countryItems.get(0).getStrArea());
        hideProgressBar();
        countriesHomeAdapter.setList(countryItems);
        countriesHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String error) {
        hideProgressBar();
        Toast.makeText(getContext(),  error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addMeal(MealsItem mealsItem) {
        homepresenter.addToFav(mealsItem);
    }

    @Override
    public void showEmptyDataMessage() {
        hideProgressBar();

    }

    @Override
    public void deleteMeals(MealsItem mealsItem) {
        homepresenter.removeFromFav(mealsItem);
    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof CategoriesItem) {
            CategoriesItem categoriesItem = (CategoriesItem) item;
            HomeFragmentDirections.ActionHomeToMealsFragment actionHomeToMealsFragment =
                    HomeFragmentDirections.actionHomeToMealsFragment();
            actionHomeToMealsFragment.setCategoryItem(categoriesItem);
            Navigation.findNavController(view).navigate(actionHomeToMealsFragment);
            //logic
           //Toast.makeText(getContext(), categoriesItem.getIdCategory(), Toast.LENGTH_SHORT).show();

        } else if (item instanceof IngredientItem) {
            IngredientItem ingredientItem = (IngredientItem) item;
            HomeFragmentDirections.ActionHomeToIngredientsFragment actionHomeToIngredientsFragment =
                    HomeFragmentDirections.actionHomeToIngredientsFragment();
            actionHomeToIngredientsFragment.setIngredientItem(ingredientItem);
            //navigate with object ingredientItem
            Navigation.findNavController(view).navigate(actionHomeToIngredientsFragment);
            //logic
            //Toast.makeText(getContext(), ingredientItem.getIdIngredient(), Toast.LENGTH_SHORT).show();
        }
        else if (item instanceof CountryItem) {
            CountryItem countryItem = (CountryItem) item;
            HomeFragmentDirections.ActionHomeToMealsFragment actionHomeToMealsFragment =
                    HomeFragmentDirections.actionHomeToMealsFragment();
            actionHomeToMealsFragment.setCountryItem(countryItem);
            Navigation.findNavController(view).navigate(actionHomeToMealsFragment);
            //logic
            //Toast.makeText(getContext(), countryItem.getStrArea(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgressBar() {
        HomeActivity.showLoadingAnimation();
    }

    private void hideProgressBar() {
        HomeActivity.hideLoadingAnimation();
    }


    public void showGuestModeMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sign Up For More Features");
        builder.setMessage("Add your food preferences, shop your recipes, plan your meals and more!");

        builder.setPositiveButton("SIGN UP", (dialog, which) -> {
            startActivity(new Intent(getActivity(), AuthenticationActivity.class));
            getActivity().finish();
        });

        builder.setNegativeButton("CANCEL", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }
}