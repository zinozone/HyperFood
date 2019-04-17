package com.project.hyperfood.activity;

import android.os.Bundle;

import com.project.hyperfood.R;
import com.project.hyperfood.common.model.Food;
import com.project.hyperfood.common.utils.FontUtil;
import com.project.hyperfood.databinding.ActivityFoodBinding;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class FoodActivity extends AbstractActivity{

    public static final String EXTRA_FOOD = "extra-food";

    private ActivityFoodBinding binding;
    private Food food;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food);

        food = getIntent().getParcelableExtra(EXTRA_FOOD);
        if (food != null){
            setData();
        }
    }

    private void setData(){
        binding.tvFoodName.setText(food.getFoodName());
        binding.tvFoodName.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
    }
}
