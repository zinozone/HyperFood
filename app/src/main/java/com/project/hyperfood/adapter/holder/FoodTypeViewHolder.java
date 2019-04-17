package com.project.hyperfood.adapter.holder;

import android.content.Intent;

import com.project.hyperfood.activity.SelectFoodActivity;
import com.project.hyperfood.common.model.FoodType;
import com.project.hyperfood.common.utils.FontUtil;
import com.project.hyperfood.databinding.ItemSelectFoodTypeBinding;

public class FoodTypeViewHolder extends ItemViewHolder<FoodType>{

    private ItemSelectFoodTypeBinding binding;

    public FoodTypeViewHolder(ItemSelectFoodTypeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    public void bindingData(FoodType foodType, int position) {
        super.bindingData(foodType, position);

        binding.tvFoodType.setTypeface(FontUtil.getFont(getContext().getAssets(), FontUtil.LAMMOON_REGULAR));
        binding.tvFoodType.setText(foodType.getFoodTypeName());
        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SelectFoodActivity.class);
            intent.putExtra(SelectFoodActivity.EXTRA_FOOD_TYPE, foodType.getFoodType());
            getContext().startActivity(intent);
        });
    }
}
