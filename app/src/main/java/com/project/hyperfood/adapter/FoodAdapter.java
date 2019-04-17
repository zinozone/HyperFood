package com.project.hyperfood.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.project.hyperfood.adapter.holder.FoodViewHolder;
import com.project.hyperfood.adapter.holder.ItemViewHolder;
import com.project.hyperfood.databinding.ItemSelectFoodBinding;

import androidx.annotation.NonNull;

public class FoodAdapter extends AbstractArrayAdapter{

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSelectFoodBinding binding = ItemSelectFoodBinding.inflate(layoutInflater, parent, false);
        return new FoodViewHolder(binding);
    }

}
