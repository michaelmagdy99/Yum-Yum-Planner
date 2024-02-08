package com.example.yumyumplanner.home.favourite.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.yumyumplanner.R;
import com.example.yumyumplanner.database.MealsLocalDataSourceImp;
import com.example.yumyumplanner.home.favourite.presenter.FavouritePresenter;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.MealsRemoteDataSourceImp;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment implements OnClickFavListener, FavouriteView{

  private View view;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    FavouritePresenter presenter;

    FavAdapter favAdapter;

    LottieAnimationView no_fav_list;
    TextView textView_no_fav;



    public FavouriteFragment() {
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
        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        initUi(view);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        favAdapter = new FavAdapter(getContext(), new ArrayList<>(), this);
        presenter =new FavouritePresenter(this,
                HomeRepositryImp.getInstance(
                        MealsRemoteDataSourceImp.getInstance(),
                        MealsLocalDataSourceImp.getInstance(getContext())),
                    BackUpDataSourceImp.getInstance(getContext())
                );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favAdapter);
        presenter.getMealsFromDB();


        return view;
    }

    private void initUi(View view){
        recyclerView = view.findViewById(R.id.fav_recyclerview);
        no_fav_list = view.findViewById(R.id.animation_view_fav);
        textView_no_fav = view.findViewById(R.id.txt_1);
    }

    @Override
    public void showData(LiveData<List<MealsItem>> allMealsFromLocal) {
        allMealsFromLocal.observe(getViewLifecycleOwner(), new Observer<List<MealsItem>>() {
                 @Override
                 public void onChanged(List<MealsItem> mealsItems) {

                     if (mealsItems != null && !mealsItems.isEmpty()) {
                         no_fav_list.setVisibility(View.GONE);
                         textView_no_fav.setVisibility(View.GONE);
                         recyclerView.setVisibility(View.VISIBLE);
                         favAdapter.setList(mealsItems);
                         favAdapter.notifyDataSetChanged();
                     } else {
                         no_fav_list.setVisibility(View.VISIBLE);
                         textView_no_fav.setVisibility(View.VISIBLE);
                         recyclerView.setVisibility(View.GONE);
                     }
                 }
            });

    }
    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(getContext(),  error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void deleteMeals(MealsItem mealsItem) {
        presenter.removeFromFav(mealsItem);
        favAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterRemove() {
        presenter.getMealsFromDB();
    }

    @Override
    public void onItemClick(MealsItem mealsItem) {
        deleteMeals(mealsItem);
    }
    @Override
    public void onDetailsItemClick(MealsItem mealsItem) {
        FavouriteFragmentDirections.ActionFavouriteToMealDetailsFragment action =
                FavouriteFragmentDirections.actionFavouriteToMealDetailsFragment();
        action.setMealDetails(mealsItem);
        Navigation.findNavController(view).navigate(action);
    }
}