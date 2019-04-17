package com.project.hyperfood.adapter.holder;


import android.content.Intent;

import com.project.hyperfood.R;
import com.project.hyperfood.activity.FoodActivity;
import com.project.hyperfood.common.model.Food;
import com.project.hyperfood.common.preferences.HPF;
import com.project.hyperfood.common.utils.FontUtil;
import com.project.hyperfood.databinding.ItemSelectFoodBinding;

import androidx.core.content.ContextCompat;

public class FoodViewHolder extends ItemViewHolder<Food>{

    private ItemSelectFoodBinding binding;

    public FoodViewHolder(ItemSelectFoodBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    public void bindingData(Food food, int position) {
        super.bindingData(food, position);

        binding.tvFood.setTypeface(FontUtil.getFont(getContext().getAssets(), FontUtil.LAMMOON_REGULAR));
        binding.tvLimit.setTypeface(FontUtil.getFont(getContext().getAssets(), FontUtil.LAMMOON_REGULAR));
        binding.tvType.setTypeface(FontUtil.getFont(getContext().getAssets(), FontUtil.LAMMOON_REGULAR));
        binding.tvContainer.setTypeface(FontUtil.getFont(getContext().getAssets(), FontUtil.LAMMOON_REGULAR));
        binding.tvFood.setText(food.getFoodName());

        if (food.getFoodType().equals(getContext().getString(R.string.fruit))){
            binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_salad));
        }else if (food.getFoodType().equals(getContext().getString(R.string.fast_food))){
            binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fast_food));
        }else if (food.getFoodType().equals(getContext().getString(R.string.side_dish))){
            binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_dish));
        }else {
            binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_cupcake));
        }

        if (HPF.getInstance().getUser().getCongenitalDisease().equals(getContext().getString(R.string.hypertension))){
            binding.tvType.setText(getContext().getString(R.string.soduim));
            binding.tvLimit.setText(food.getSoduim() + " มิลลิกรัม");
        }else if (HPF.getInstance().getUser().getCongenitalDisease().equals(getContext().getString(R.string.obesity))){
            binding.tvType.setText(getContext().getString(R.string.fat));
            binding.tvLimit.setText(food.getFat() + " กรัม");
        }else {
            binding.tvType.setText(getContext().getString(R.string.carbohydrate));
            binding.tvLimit.setText(food.getCarbohydrate() + " กรัม");
        }
        binding.tvContainer.setText("ต่อ 1 " +food.getContainer());

        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FoodActivity.class);
            intent.putExtra(FoodActivity.EXTRA_FOOD, food);
            getContext().startActivity(intent);
        });
    }
}
