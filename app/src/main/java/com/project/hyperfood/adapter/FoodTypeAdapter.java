package com.project.hyperfood.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.project.hyperfood.adapter.holder.FoodTypeViewHolder;
import com.project.hyperfood.adapter.holder.ItemViewHolder;
import com.project.hyperfood.databinding.ItemSelectFoodTypeBinding;

import androidx.annotation.NonNull;

public class FoodTypeAdapter extends AbstractArrayAdapter{

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSelectFoodTypeBinding binding = ItemSelectFoodTypeBinding.inflate(layoutInflater, parent, false);
        return new FoodTypeViewHolder(binding);
    }

}
