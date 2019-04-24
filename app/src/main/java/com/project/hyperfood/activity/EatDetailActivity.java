package com.project.hyperfood.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.hyperfood.R;
import com.project.hyperfood.common.model.Food;
import com.project.hyperfood.common.preferences.HPF;
import com.project.hyperfood.common.utils.DateTimeUtils;
import com.project.hyperfood.databinding.ActivityEatDetailBinding;
import com.project.hyperfood.databinding.ItemFoodDetailBinding;

import static com.project.hyperfood.application.HyperFoodApplication.USER_FOOD;

public class EatDetailActivity extends AbstractActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String EXTRA_DATE = "extra-date";

    private ActivityEatDetailBinding binding;
    private String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_eat_detail);
        binding.refreshLayout.setOnRefreshListener(this);

        date = getIntent().getStringExtra(EXTRA_DATE);
        getFood();
    }

    private void getFood(){
        binding.refreshLayout.setRefreshing(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference(USER_FOOD)
                .child(user.getUid())
                .child(DateTimeUtils.getDateSaveFood(date));
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binding.refreshLayout.setRefreshing(false);

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_morning)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    addView(binding.viewMorning, food);
                }

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_noon)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    addView(binding.viewNoon, food);
                }

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_evening)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    addView(binding.viewEvening, food);
                }

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_night)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    addView(binding.viewNight, food);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                binding.refreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        };
        foodRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void addView(ViewGroup view, Food food){
        LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
        ItemFoodDetailBinding foodDetailBinding = ItemFoodDetailBinding.inflate(layoutInflater, view, false);
        bindFood(foodDetailBinding, food);
        View rowView = foodDetailBinding.getRoot();
        view.addView(rowView, view.getChildCount());
    }

    private void bindFood(ItemFoodDetailBinding binding, Food food){
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
            binding.tvLimit.setText(food.getSoduim() + " " + getContext().getString(R.string.mili_grams));
        }else if (HPF.getInstance().getUser().getCongenitalDisease().equals(getContext().getString(R.string.obesity))){
            binding.tvType.setText(getContext().getString(R.string.quantity));
            binding.tvLimit.setText(food.getKcal() + " " + getContext().getString(R.string.kcal));
        }else {
            binding.tvType.setText(getContext().getString(R.string.carbohydrate));
            binding.tvLimit.setText(food.getCarbohydrate() +  " " + getContext().getString(R.string.grams));
        }
        binding.tvContainer.setText("ต่อ 1 " +food.getContainer());
    }

    @Override
    public void onRefresh() {
        binding.refreshLayout.setRefreshing(false);
    }
}
