package com.example.yumyumplanner.home.ingredient_details.view;

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
import com.example.yumyumplanner.home.home.view.OnClickListener;
import com.example.yumyumplanner.model.data.Item;

import java.util.List;

public class MealByIngrAdapter extends RecyclerView.Adapter<MealByIngrAdapter.CategoryViewHolder> {
    private Context context;
    private List<Item> filterItems;
    private OnClickListener<Item> listener;


    public MealByIngrAdapter(Context context, List<Item> filterItems, OnClickListener<Item> listener){
        this.context = context;
        this.filterItems = filterItems;
        this.listener = listener;

    }
    public void setList(List<Item> filterItems) {
        this.filterItems = filterItems;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_home_item,parent,false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Item item = filterItems.get(position);

        String ingredient = item.getName();

        holder.categoryName.setText(ingredient);

        Glide.with(context)
                .load(item.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.categoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filterItems.size();
    }



    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryImage;
        private TextView categoryName;
        private View view;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            categoryImage = itemView.findViewById(R.id.image_of_category_home);
            categoryName = itemView.findViewById(R.id.category_name_home);
        }
    }
}
