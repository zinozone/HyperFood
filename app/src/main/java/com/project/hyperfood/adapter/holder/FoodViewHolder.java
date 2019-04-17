package com.project.hyperfood.adapter.holder;


import com.project.hyperfood.common.model.Food;
import com.project.hyperfood.common.utils.FontUtil;
import com.project.hyperfood.databinding.ItemSelectFoodBinding;

public class FoodViewHolder extends ItemViewHolder<Food>{

    private ItemSelectFoodBinding binding;

    public FoodViewHolder(ItemSelectFoodBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    public void bindingData(Food food, int position) {
        super.bindingData(food, position);

        binding.tvFood.setTypeface(FontUtil.getFont(getContext().getAssets(), FontUtil.LAMMOON));
        binding.tvFood.setText(food.getFoodName());
    }
}
