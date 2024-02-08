package com.example.yumyumplanner.home.favourite.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yumyumplanner.R;
import com.example.yumyumplanner.model.data.MealsItem;

import java.util.List;

public class FavAdapter  extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private Context context;
    private List<MealsItem> mealsItemList;
    private OnClickFavListener listener;


    public FavAdapter(Context context, List<MealsItem> mealsItemList, OnClickFavListener listener) {
        this.context = context;
        this.mealsItemList = mealsItemList;
        this.listener = listener;

    }


    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fav_item, parent, false);
        FavViewHolder favViewHolder = new FavViewHolder(view);
        return favViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        holder.name.setText(mealsItemList.get(position).getStrMeal());
        holder.category.setText(mealsItemList.get(position).getStrCategory());
        holder.country.setText(mealsItemList.get(position).getStrArea());


        Glide.with(context)
                .load(mealsItemList.get(position).getStrMealThumb())
                .centerCrop()
                .placeholder(R.drawable.cooking)
                .into(holder.photo);

        holder.favBtn.setOnClickListener(new View.OnClickListener() {
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

    public void setList(List<MealsItem> mealsItemList) {
        this.mealsItemList = mealsItemList;
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo;
        private TextView name;
        private TextView country;
        private TextView category;
        private View view;

        private ImageView favBtn;
        private ConstraintLayout itemConstraint;


        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            photo = itemView.findViewById(R.id.image_meal);
            name = itemView.findViewById(R.id.name_meal);
            country = itemView.findViewById(R.id.country_meal);
            category = itemView.findViewById(R.id.category_meal);
            favBtn = itemView.findViewById(R.id.fav_btn_home);
            itemConstraint = view.findViewById(R.id.item_constrian);

        }
    }
}
