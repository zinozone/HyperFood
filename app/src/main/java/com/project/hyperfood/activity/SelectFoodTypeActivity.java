package com.project.hyperfood.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.hyperfood.R;
import com.project.hyperfood.adapter.FoodTypeAdapter;
import com.project.hyperfood.application.HyperFoodApplication;
import com.project.hyperfood.common.model.FoodType;
import com.project.hyperfood.common.utils.FontUtil;
import com.project.hyperfood.databinding.ActivitySelectFoodTypeBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.project.hyperfood.application.HyperFoodApplication.FOOD_TYPE;

public class SelectFoodTypeActivity extends AbstractActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ActivitySelectFoodTypeBinding binding;
    private FoodTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_food_type);

        binding.tvTitle.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        binding.tvTitle.setText(HyperFoodApplication.menuTitle);
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.rvFoodType.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.refreshLayout.setOnRefreshListener(this);

        adapter = new FoodTypeAdapter();
        binding.rvFoodType.setAdapter(adapter);

        getFoodType();
    }

    private void getFoodType(){
        binding.refreshLayout.setRefreshing(true);
        DatabaseReference foodTypeRef = FirebaseDatabase.getInstance().getReference(FOOD_TYPE);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binding.refreshLayout.setRefreshing(false);
                List<FoodType> foodTypes = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    FoodType foodType = snapshot.getValue(FoodType.class);
                    foodTypes.add(foodType);
                }

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
        foodTypeRef.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void onRefresh() {
        getFoodType();
    }
}
