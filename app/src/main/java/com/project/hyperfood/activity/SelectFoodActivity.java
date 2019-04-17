package com.project.hyperfood.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.hyperfood.R;
import com.project.hyperfood.adapter.FoodAdapter;
import com.project.hyperfood.application.HyperFoodApplication;
import com.project.hyperfood.common.model.Food;
import com.project.hyperfood.common.utils.FontUtil;
import com.project.hyperfood.databinding.ActivitySelectFoodBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.project.hyperfood.application.HyperFoodApplication.FOOD;

public class SelectFoodActivity extends AbstractActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String EXTRA_FOOD_TYPE = "extra-food-type";

    private ActivitySelectFoodBinding binding;
    private FoodAdapter adapter;
    private String foodType;
    private List<Food> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_food);

        binding.tvTitle.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        binding.tvTitle.setText(HyperFoodApplication.menuTitle);
        binding.edtSearch.addTextChangedListener(textWatcher);
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.rvFood.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.refreshLayout.setOnRefreshListener(this);

        foodType = getIntent().getStringExtra(EXTRA_FOOD_TYPE);

        adapter = new FoodAdapter();
        binding.rvFood.setAdapter(adapter);

        getFood();
    }

    private void getFood(){
        binding.refreshLayout.setRefreshing(true);
        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference(FOOD).child(foodType);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binding.refreshLayout.setRefreshing(false);
                List<Food> foodTypes = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    food.setFoodType(foodType);
                    foodTypes.add(food);
                }

                data = foodTypes;
                if (adapter.getItemCount()>0){
                    adapter.clear();
                }
                adapter.addAll(foodTypes);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                binding.refreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        };
        foodRef.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void onRefresh() {
        getFood();
    }

    private void searchData(CharSequence s){
        List<Food> foodList = new ArrayList<>();
        if (data != null && data.size() > 0) {
            for (Food food : data) {
                if (food.getFoodName().contains(s)) {
                    foodList.add(food);
                }
            }
        }
        adapter.clear();
        adapter.addAll(foodList);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchData(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
