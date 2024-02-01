package com.example.yumyumplanner.home.calendar.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yumyumplanner.R;
import com.example.yumyumplanner.home.favourite.view.FavAdapter;
import com.example.yumyumplanner.home.favourite.view.OnClickFavListener;
import com.example.yumyumplanner.model.data.MealCalendar;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.CalenderViewHolder> {

    private Context context;
    private List<MealCalendar> mealsItemList;
    private OnClickPlanListener listener;

    public CalenderAdapter(Context context, List<MealCalendar> mealsItemList, OnClickPlanListener listener) {
        this.context = context;
        this.mealsItemList = mealsItemList;
        this.listener = listener;

    }
    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plan_item, parent, false); // Replace with the correct layout
        CalenderViewHolder calViewHolder = new CalenderViewHolder(view);
        return calViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position) {

        holder.name.setText(mealsItemList.get(position).getStrMeal());
        holder.category.setText(mealsItemList.get(position).getStrCategory());
        holder.country.setText(mealsItemList.get(position).getStrArea());


        Glide.with(context)
                .load(mealsItemList.get(position).getStrMealThumb())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.photo);
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mealsItemList.get(position));
            }
        });
        holder.itemConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDetailsItemClick(mealsItemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealsItemList.size();
    }

    public void setList(List<MealCalendar> mealsItemList) {
        this.mealsItemList = mealsItemList;
    }

    public static class CalenderViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private TextView name;
        private TextView country;
        private TextView category;
        private View view;

        private Button removeBtn;
        private ConstraintLayout itemConstraint;
        public CalenderViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            photo = itemView.findViewById(R.id.image_meal_plan);
            name = itemView.findViewById(R.id.name_meal_plan);
            country = itemView.findViewById(R.id.country_meal_plan);
            category = itemView.findViewById(R.id.category_meal_plan);
            removeBtn = itemView.findViewById(R.id.remove_to_calender);
            itemConstraint = view.findViewById(R.id.item_constrian_plan);
        }
    }
}
