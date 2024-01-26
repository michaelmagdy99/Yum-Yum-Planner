package com.example.yumyumplanner.home.home.view;

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
import com.example.yumyumplanner.model.data.IngredientItem;

import java.util.List;

public class IngrdientsHomeAdapter extends RecyclerView.Adapter<IngrdientsHomeAdapter.IngrdientsViewHolder> {
    private Context context;
    private List<IngredientItem> ingrediantsList;
    private OnClickListener<IngredientItem> listener;

    public IngrdientsHomeAdapter(Context context, List<IngredientItem> ingrediantsList, OnClickListener<IngredientItem> listener){
        this.context = context;
        this.ingrediantsList = ingrediantsList;
        this.listener = listener;

    }
    public void setList(List<IngredientItem> ingredientItems) {
        this.ingrediantsList = ingredientItems;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public IngrdientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredients_home_items,parent,false);
        IngrdientsViewHolder ingrdientsViewHolder = new IngrdientsViewHolder(view);
        return ingrdientsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngrdientsViewHolder holder, int position) {
        String ingredient = ingrediantsList.get(position).getStrIngredient();
        String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredient + ".png";

        holder.ingrdientsTextView.setText(ingredient);

        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ingtrdientsImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(ingrediantsList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return ingrediantsList.size();
    }



    public static class IngrdientsViewHolder extends RecyclerView.ViewHolder {

        private ImageView ingtrdientsImage;
        private TextView ingrdientsTextView;
        private View view;
        public IngrdientsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ingtrdientsImage = itemView.findViewById(R.id.image_of_ingradines_home);
            ingrdientsTextView = itemView.findViewById(R.id.ingradents_name_home);
        }
    }
}
