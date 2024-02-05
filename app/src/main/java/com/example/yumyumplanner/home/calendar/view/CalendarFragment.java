package com.example.yumyumplanner.home.calendar.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.database.MealsLocalDataSourceImp;
import com.example.yumyumplanner.home.calendar.presenter.CalenderPresenter;
import com.example.yumyumplanner.home.favourite.view.FavouriteFragmentDirections;
import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.meals_repo.HomeRepositryImp;
import com.example.yumyumplanner.remote.api.MealsRemoteDataSourceImp;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment implements CalenderViewInterface, OnClickPlanListener {

    private View view;

    RecyclerView recyclerView;

    Button addToPlan;
    CalendarView calendarViwe;
    LinearLayoutManager layoutManager;
    CalenderPresenter presenter;

    CalenderAdapter calenderAdapter;
    String date;
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

        recyclerView = view.findViewById(R.id.recycler_view_planner_meals);
        calendarViwe = view.findViewById(R.id.calendar_view);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        calenderAdapter = new CalenderAdapter(getContext(), new ArrayList<>(),this);

        presenter =new CalenderPresenter(this,
                HomeRepositryImp.getInstance(
                        MealsRemoteDataSourceImp.getInstance(),
                        MealsLocalDataSourceImp.getInstance(getContext())),
                BackUpDataSourceImp.getInstance(getContext())
        );


        //set data to get data
        calendarViwe.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                Log.e("date",String.valueOf(dayOfMonth) + "-" + (month + 1) + "-" + year);
                String currentDate = String.valueOf(dayOfMonth) + "-" + (month + 1) + "-" + year;
                recyclerView.setLayoutManager(layoutManager);
                //setAdapter
                recyclerView.setAdapter(calenderAdapter);
                presenter.getMealsFromCalendar(currentDate);
            }
        });


        return view;
    }


    @Override
    public void showData(List<MealCalendar> allMealsCaleander) {
        calenderAdapter.setList(allMealsCaleander);
        calenderAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void deleteMeals(MealCalendar mealCalendar) {
        presenter.removeFromCalnder(mealCalendar);
    }

    @Override
    public void afterRemove() {
       // presenter.getMealsFromCalendar(date);
    }

    @Override
    public void onItemClick(MealCalendar mealsItem) {
        deleteMeals(mealsItem);
        Toast.makeText(getContext(), "removed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetailsItemClick(MealCalendar mealsItem) {
        CalendarFragmentDirections.ActionCalendarToMealDetailsFragment action =
                CalendarFragmentDirections.actionCalendarToMealDetailsFragment();
        action.setMealCalender(mealsItem);
        Navigation.findNavController(view).navigate(action);
    }
}