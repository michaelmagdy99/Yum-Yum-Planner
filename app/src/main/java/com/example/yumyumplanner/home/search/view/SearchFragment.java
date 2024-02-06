package com.example.yumyumplanner.home.search.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.database.MealsLocalDataSourceImp;
import com.example.yumyumplanner.home.home.presenter.HomePresenter;
import com.example.yumyumplanner.home.home.view.CategoryHomeAdapter;
import com.example.yumyumplanner.home.home.view.CountriesHomeAdapter;
import com.example.yumyumplanner.home.home.view.HomeFragmentDirections;
import com.example.yumyumplanner.home.home.view.IngrdientsHomeAdapter;
import com.example.yumyumplanner.home.home.view.OnClickListener;
import com.example.yumyumplanner.home.search.presenter.SearchPresenter;
import com.example.yumyumplanner.home.search.presenter.SearchPresenterImp;
import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.CountryItem;
import com.example.yumyumplanner.model.data.IngredientItem;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.MealsRemoteDataSourceImp;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.divider.MaterialDivider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class SearchFragment extends Fragment implements SearchView, OnClickListener {

    private RecyclerView ingradientRecyclerView;
    private IngrdientsHomeAdapter ingtrdientsHomeAdapter;

    LinearLayoutManager IngrlayoutManager;
    GridLayoutManager caterlayoutManager;

    private CategoryHomeAdapter categoryHomeAdapter;

    private CountriesHomeAdapter countriesHomeAdapter;

    private LinearLayoutManager countryLayoutManager;
    private RecyclerView countryRecyclerView;
    private RecyclerView categoryRecyclerView;

    private ProgressDialog progressDialog;

    private SearchPresenterImp searchPresenterImp;
    private View view;
    private ChipGroup chipGroup ;

    EditText searchBar;
    TextView ingtTV;
    TextView counTV;
    TextView catTV;
    MaterialDivider divider;
    MaterialDivider divider1;
    public SearchFragment() {
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
        view = inflater.inflate(R.layout.fragment_search, container, false);


        intitUI(view);
        showProgressBar();
        //object of ingtrdientsHomeAdapter
        IngrlayoutManager = new LinearLayoutManager(getContext());
        IngrlayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ingtrdientsHomeAdapter = new IngrdientsHomeAdapter(getContext(), new ArrayList<>(),this);

        //object of country
        countryLayoutManager = new LinearLayoutManager(getContext());
        countryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        countriesHomeAdapter = new CountriesHomeAdapter(getContext(), new ArrayList<>(),this);

        //object of categoryHomeAdapter
        caterlayoutManager = new GridLayoutManager(getContext(), 2);
        caterlayoutManager.setOrientation(RecyclerView.VERTICAL);
        categoryHomeAdapter = new CategoryHomeAdapter(getContext(), new ArrayList<>(), this);
        searchPresenterImp =new SearchPresenterImp(this,
                HomeRepositryImp.getInstance(
                        MealsRemoteDataSourceImp.getInstance(), MealsLocalDataSourceImp.getInstance(getContext())));
        //ingradientsRV
        ingradientRecyclerView.setLayoutManager(IngrlayoutManager);
        ingradientRecyclerView.setAdapter(ingtrdientsHomeAdapter);

        //countrysRV
        countryRecyclerView.setLayoutManager(countryLayoutManager);
        countryRecyclerView.setAdapter(countriesHomeAdapter);
        //categoryRV
        categoryRecyclerView.setLayoutManager(caterlayoutManager);
        categoryRecyclerView.setAdapter(categoryHomeAdapter);

        //ingradient
        searchPresenterImp.getIngredients();
        //categoty
        searchPresenterImp.getCategories();
        //country
        searchPresenterImp.getCountry();

        setUpChipGroup();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().toLowerCase();
                searchPresenterImp.searchByCategoryText(searchText);
                searchPresenterImp.searchByIngrText(searchText);
                searchPresenterImp.searchByCountryText(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void intitUI(View view){
        ingradientRecyclerView = view.findViewById(R.id.ingredients_recycler_search);
        categoryRecyclerView = view.findViewById(R.id.category_recycler_search);
        countryRecyclerView = view.findViewById(R.id.country_recycler_search);

        catTV = view.findViewById(R.id.category_view);
        counTV = view.findViewById(R.id.country_view);
        ingtTV =view.findViewById(R.id.ingredients_view);
        divider = view.findViewById(R.id.divider_search);
        divider1 = view.findViewById(R.id.divider_search_1);

        chipGroup = view.findViewById(R.id.chipGroup);

        searchBar = view.findViewById(R.id.searchEditText);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait... It is downloading");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

    }



    @Override
    public void showIngredients(List<IngredientItem> ingredientItems) {
        hideProgressBar();
        Log.i("TAG", "showDataIngr: " + ingredientItems.get(0).getStrIngredient());
        ingtrdientsHomeAdapter.setList(ingredientItems);
        ingtrdientsHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategory(List<CategoriesItem> categoriesItems) {
        Log.i("TAG", "showDataCateg: " + categoriesItems.get(0).getStrCategory());
        hideProgressBar();
        categoryHomeAdapter.setList(categoriesItems);
        categoryHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCounrty(List<CountryItem> countryItem) {
        Log.i("TAG", "showDataCateg: " + countryItem.get(0).getStrArea());
        hideProgressBar();
        countriesHomeAdapter.setList(countryItem);
        countriesHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String error) {
        hideProgressBar();
        Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addMeal(MealsItem mealsItem) {
        searchPresenterImp.addToFav(mealsItem);

    }

    @Override
    public void showEmptyDataMessage() {
        hideProgressBar();

    }

    private void showProgressBar() {
        progressDialog.show();
    }

    private void hideProgressBar() {
        progressDialog.dismiss();
    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof CategoriesItem) {
            CategoriesItem categoriesItem = (CategoriesItem) item;
            SearchFragmentDirections.ActionSearchToMealsFragment actionHomeToMealsFragment =
                    SearchFragmentDirections.actionSearchToMealsFragment();
            actionHomeToMealsFragment.setCategoryItem(categoriesItem);
            Navigation.findNavController(view).navigate(actionHomeToMealsFragment);
            //logic
            //Toast.makeText(getContext(), categoriesItem.getIdCategory(), Toast.LENGTH_SHORT).show();

        } else if (item instanceof IngredientItem) {
            IngredientItem ingredientItem = (IngredientItem) item;
            SearchFragmentDirections.ActionSearchToIngredientsFragment actionHomeToIngredientsFragment =
                    SearchFragmentDirections.actionSearchToIngredientsFragment();
            actionHomeToIngredientsFragment.setIngredientItem(ingredientItem);
            //navigate with object ingredientItem
            Navigation.findNavController(view).navigate(actionHomeToIngredientsFragment);
            //logic
            //Toast.makeText(getContext(), ingredientItem.getIdIngredient(), Toast.LENGTH_SHORT).show();
        }
        else if (item instanceof CountryItem) {
            CountryItem countryItem = (CountryItem) item;
            SearchFragmentDirections.ActionSearchToMealsFragment actionHomeToMealsFragment =
                    SearchFragmentDirections.actionSearchToMealsFragment();
            actionHomeToMealsFragment.setCountryItem(countryItem);
            Navigation.findNavController(view).navigate(actionHomeToMealsFragment);
            //logic
            //Toast.makeText(getContext(), countryItem.getStrArea(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpChipGroup(){
        for (int i=0; i<chipGroup.getChildCount() ; i++){
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        showProgressBar();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                hideAllViews();
                                filterSearch();
                            }
                        });

                    }else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                countryLayoutManager = new LinearLayoutManager(getContext());
                                countryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                                countryRecyclerView.setLayoutManager(countryLayoutManager);
                                IngrlayoutManager = new LinearLayoutManager(getContext());
                                IngrlayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                                ingradientRecyclerView.setLayoutManager(IngrlayoutManager);
                            }
                        });
                        ingradientRecyclerView.setVisibility(View.VISIBLE);
                        categoryRecyclerView.setVisibility(View.VISIBLE);
                        countryRecyclerView.setVisibility(View.VISIBLE);
                        catTV.setVisibility(View.VISIBLE);
                        ingtTV.setVisibility(View.VISIBLE);
                        counTV.setVisibility(View.VISIBLE);
                        divider.setVisibility(View.VISIBLE);
                        divider1.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    private void filterSearch(){
        String searchText = searchBar.getText().toString().toLowerCase();

        Chip selectedChip = chipGroup.findViewById(chipGroup.getCheckedChipId());
        if (selectedChip != null) {
            String chipText = selectedChip.getText().toString();

            switch (chipText) {
                case "Ingredients":
                    //make ingredienta gride
                    IngrlayoutManager = new GridLayoutManager(getContext(), 3);
                    ingradientRecyclerView.setLayoutManager(IngrlayoutManager);
                    searchPresenterImp.searchByIngrText(searchText);
                    ingradientRecyclerView.setVisibility(View.VISIBLE);
                    ingtTV.setVisibility(View.VISIBLE);

                    break;
                case "Categories":
                    searchPresenterImp.searchByCategoryText(searchText);
                    categoryRecyclerView.setVisibility(View.VISIBLE);
                    catTV.setVisibility(View.VISIBLE);
                    break;
                case "Country":
                    countryLayoutManager = new GridLayoutManager(getContext(), 3);
                    countryRecyclerView.setLayoutManager(countryLayoutManager);
                    searchPresenterImp.searchByCountryText(searchText);
                    countryRecyclerView.setVisibility(View.VISIBLE);
                    counTV.setVisibility(View.VISIBLE);
                    break;
                default:
                    searchPresenterImp.searchByCountryText(searchText);
                    searchPresenterImp.searchByCategoryText(searchText);
                    searchPresenterImp.searchByIngrText(searchText);
                    break;
            }
        }
    }

    private void hideAllViews() {
        ingradientRecyclerView.setVisibility(View.GONE);
        categoryRecyclerView.setVisibility(View.GONE);
        countryRecyclerView.setVisibility(View.GONE);
        catTV.setVisibility(View.GONE);
        ingtTV.setVisibility(View.GONE);
        counTV.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
        divider1.setVisibility(View.GONE);
    }
}