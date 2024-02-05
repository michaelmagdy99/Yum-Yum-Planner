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
import com.example.yumyumplanner.model.data.CountryItem;
import com.example.yumyumplanner.model.data.IngredientItem;

import java.util.List;

public class CountriesHomeAdapter extends RecyclerView.Adapter<CountriesHomeAdapter.CountriesViewModel> {

    private Context context;
    private List<CountryItem> countrysList;
    private OnClickListener<CountryItem> listener;

    public CountriesHomeAdapter(Context context, List<CountryItem> countrysList, OnClickListener<CountryItem> listener){
        this.context = context;
        this.countrysList = countrysList;
        this.listener = listener;

    }
    @NonNull
    @Override
    public CountriesViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.country_item,parent,false);
        CountriesViewModel countriesViewModel = new CountriesViewModel(view);
        return countriesViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewModel holder, int position) {
        String country = countrysList.get(position).getStrArea();
        String countryCode = CountryItem.getUrlForCountry(country);
        String imageUrl = "https://www.themealdb.com/images/icons/flags/big/64/" + countryCode + ".png";

        holder.countryTextView.setText(country);

        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.countryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(countrysList.get(position));
            }
        });
    }

    public void setList(List<CountryItem> countryItem) {
        this.countrysList = countryItem;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return countrysList.size();
    }


    public static class CountriesViewModel extends RecyclerView.ViewHolder {
        private ImageView countryImage;
        private TextView countryTextView;
        private View view;
        public CountriesViewModel(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            countryImage = itemView.findViewById(R.id.image_of_country_home);
            countryTextView = itemView.findViewById(R.id.country_name_home);
        }
    }
}
