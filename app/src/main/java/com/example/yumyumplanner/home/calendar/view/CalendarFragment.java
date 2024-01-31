package com.example.yumyumplanner.home.calendar.view;

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
import com.example.yumyumplanner.home.calendar.presenter.CalenderPresenter;
import com.example.yumyumplanner.home.favourite.presenter.FavouritePresenter;
import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;


public class CalendarFragment extends Fragment implements CalenderView {

    private View view;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    CalenderPresenter presenter;
    public CalendarFragment() {
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
        view = inflater.inflate(R.layout.fragment_calendar, container, false);

        initUi(view);
//        layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//
//
//        recyclerView.setLayoutManager(layoutManager);
        //setAdapter
       // recyclerView.setAdapter(favAdapter);
        //set data to get data
        //presenter.getMealsFromCalendar();

        return view;
    }

    private void initUi(View view) {
    }

    @Override
    public void showData(LiveData<List<MealCalendar>> allMealsCaleander) {
            allMealsCaleander.observe(this, new Observer<List<MealCalendar>>() {
                @Override
                public void onChanged(List<MealCalendar> mealCalendars) {
                    //adapter
                }
            });
    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void deleteMeals(MealCalendar mealCalendar) {
        presenter.removeFromCalnder(mealCalendar);
    }
}