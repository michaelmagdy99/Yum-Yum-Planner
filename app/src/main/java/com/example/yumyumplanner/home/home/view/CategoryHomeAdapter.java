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
import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.IngredientItem;

import java.util.List;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.CategoryViewHolder> {
    private Context context;
    private List<CategoriesItem> categoriesItems;
    private OnClickListener<CategoriesItem> listener;


    public CategoryHomeAdapter(Context context, List<CategoriesItem> categoriesItems, OnClickListener<CategoriesItem> listener){
        this.context = context;
        this.categoriesItems = categoriesItems;
        this.listener = listener;

    }
    public void setList(List<CategoriesItem> categoriesItems) {
        this.categoriesItems = categoriesItems;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_home_list_item,parent,false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String ingredient = categoriesItems.get(position).getStrCategory();

        holder.categoryName.setText(ingredient);

        Glide.with(context)
                .load(categoriesItems.get(position).getStrCategoryThumb())
                .centerCrop()
                .placeholder(R.drawable.cooking)
                .into(holder.categoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(categoriesItems.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoriesItems.size();
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
