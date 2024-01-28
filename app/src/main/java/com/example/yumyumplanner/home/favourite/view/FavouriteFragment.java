package com.example.yumyumplanner.home.favourite.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.database.MealsLocalDataSourceImp;
import com.example.yumyumplanner.home.favourite.presenter.FavouritePresenter;
import com.example.yumyumplanner.home.home.presenter.HomePresenter;
import com.example.yumyumplanner.model.data.MealsItem;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.network.MealsRemoteDataSourceImp;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment implements OnClickFavListener, FavouriteView{

  private View view;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    FavouritePresenter presenter;

    FavAdapter favAdapter;



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
                        MealsLocalDataSourceImp.getInstance(getContext())));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favAdapter);
        presenter.getMealsFromDB();

        return view;
    }

    private void initUi(View view){
        recyclerView = view.findViewById(R.id.fav_recyclerview);
    }

    @Override
    public void showData(LiveData<List<MealsItem>> allMealsFromLocal) {
        allMealsFromLocal.observe(this, new Observer<List<MealsItem>>() {
            @Override
            public void onChanged(List<MealsItem> productList) {
                favAdapter.setList(productList);
                favAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void deleteMeals(MealsItem mealsItem) {
        presenter.removeFromFav(mealsItem);
    }

    @Override
    public void onItemClick(MealsItem mealsItem) {
        deleteMeals(mealsItem);
        Toast.makeText(getContext(), "removed", Toast.LENGTH_SHORT).show();
    }
}