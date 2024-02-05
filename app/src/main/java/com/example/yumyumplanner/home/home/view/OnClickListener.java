package com.example.yumyumplanner.home.home.view;

import com.example.yumyumplanner.model.data.CategoriesItem;
import com.example.yumyumplanner.model.data.Item;

public interface OnClickListener<T> {
    void onItemClick(T item);
}

