package com.example.yumyumplanner.home.meal_details.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yumyumplanner.R;

import java.util.List;

public class IngrdientsAdapter extends RecyclerView.Adapter<IngrdientsAdapter.IngrdientsViewHolder> {
    private Context context;
    private List<String> ingrediantsList;
    private List<String> meausreList;

    public IngrdientsAdapter(Context context, List<String> ingrediantsList, List<String> meausreList ){
        this.context = context;
        this.ingrediantsList = ingrediantsList;
        this.meausreList = meausreList;

    }
    @NonNull
    @Override
    public IngrdientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredients_items,parent,false);
        IngrdientsViewHolder ingrdientsViewHolder = new IngrdientsViewHolder(view);
        return ingrdientsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngrdientsViewHolder holder, int position) {
        String ingredient = ingrediantsList.get(position);
        String meaurse = meausreList.get(position);
        String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredient + ".png";

        holder.ingrdientsTextView.setText(ingredient);
        holder.measureTextView.setText(meaurse);
        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ingtrdientsImage);

    }

    @Override
    public int getItemCount() {
        return ingrediantsList.size();
    }

    public static class IngrdientsViewHolder extends RecyclerView.ViewHolder {

        private ImageView ingtrdientsImage;
        private TextView ingrdientsTextView;
        private View view;
        private TextView measureTextView;
        public IngrdientsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            ingtrdientsImage = itemView.findViewById(R.id.image_of_ingradines);
            ingrdientsTextView = itemView.findViewById(R.id.ingradents_name);
            measureTextView = itemView.findViewById(R.id.measure_name);
        }
    }
}
