package com.example.yumyumplanner.home.calendar.view;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.yumyumplanner.R;
import com.example.yumyumplanner.database.MealsLocalDataSourceImp;
import com.example.yumyumplanner.home.calendar.presenter.CalenderPresenter;
import com.example.yumyumplanner.home.favourite.view.FavouriteFragmentDirections;
import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;
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

    LottieAnimationView no_cal_list;
    TextView textView_no_cal;
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

        no_cal_list = view.findViewById(R.id.animation_view_cal);
        textView_no_cal = view.findViewById(R.id.txt_cal);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        calenderAdapter = new CalenderAdapter(getContext(), new ArrayList<>(),this);

        presenter =new CalenderPresenter(this,
                HomeRepositryImp.getInstance(
                        MealsRemoteDataSourceImp.getInstance(),
                        MealsLocalDataSourceImp.getInstance(getContext())),
                BackUpDataSourceImp.getInstance(getContext())
        );


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        long minDate = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        long maxDate = calendar.getTimeInMillis();
        calendarViwe.setMinDate(minDate);
        calendarViwe.setMaxDate(maxDate);
        recyclerView.setLayoutManager(layoutManager);
        //setAdapter
        int dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        Log.i("TAG", "onCreateView: " + String.valueOf(dayOfMonth) + "-" + (month + 1) + "-" + year);
        presenter.getMealsFromCalendar(String.valueOf(dayOfMonth) + "-" + (month + 1) + "-" + year);
        recyclerView.setAdapter(calenderAdapter);
        calenderAdapter.notifyDataSetChanged();
        calendarViwe.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                Log.e("date",String.valueOf(dayOfMonth) + "-" + (month + 1) + "-" + year);
                date = String.valueOf(dayOfMonth) + "-" + (month + 1) + "-" + year;

                recyclerView.setLayoutManager(layoutManager);
                //setAdapter
                recyclerView.setAdapter(calenderAdapter);
                presenter.getMealsFromCalendar(date);
            }
        });


        return view;
    }

    public void showPlanMessage(MealCalendar mealsItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are You Sure to remove Item From Your Calender ?");
        builder.setMessage("");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            deleteMeals(mealsItem);
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public void showData(List<MealCalendar> allMealsCaleander) {
        if(allMealsCaleander != null && !allMealsCaleander.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            no_cal_list.setVisibility(View.GONE);
            textView_no_cal.setVisibility(View.GONE);
            calenderAdapter.setList(allMealsCaleander);
            calenderAdapter.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.GONE);
            no_cal_list.setVisibility(View.VISIBLE);
            textView_no_cal.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(getContext(),  error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void deleteMeals(MealCalendar mealCalendar) {
        presenter.removeFromCalnder(mealCalendar);
    }

    @Override
    public void afterRemove() {
        presenter.getMealsFromCalendar(date);
    }

    @Override
    public void onItemClick(MealCalendar mealsItem) {
        showPlanMessage(mealsItem);
    }

    @Override
    public void onDetailsItemClick(MealCalendar mealsItem) {
        CalendarFragmentDirections.ActionCalendarToMealDetailsFragment action =
                CalendarFragmentDirections.actionCalendarToMealDetailsFragment();
        action.setMealCalender(mealsItem);
        Navigation.findNavController(view).navigate(action);
    }
}