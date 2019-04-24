package com.project.hyperfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.hyperfood.R;
import com.project.hyperfood.application.HyperFoodApplication;
import com.project.hyperfood.common.model.Food;
import com.project.hyperfood.common.preferences.HPF;
import com.project.hyperfood.common.utils.DatePickerFragment;
import com.project.hyperfood.common.utils.DateTimeUtils;
import com.project.hyperfood.common.utils.FontUtil;
import com.project.hyperfood.databinding.ActivityReportBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import java.text.DecimalFormat;

import static com.project.hyperfood.application.HyperFoodApplication.USER_FOOD;

public class ReportActivity extends AbstractActivity{

    private ActivityReportBinding binding;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);

        binding.tvAppName.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        binding.btnRecommend.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        binding.btnSave.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        binding.tvDate.setText(DateTimeUtils.getCurrentDate());

        setClickEvent();
        getFood();
        setMaxProgress();
        updateValue();
    }

    private void setClickEvent(){
        binding.btnDate.setOnClickListener(v -> selectDate());
        binding.tvDate.setOnClickListener(v -> selectDate());
        binding.btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SettingActivity.class));
            overridePendingTransitionEnter();
        });
        binding.btnRecommend.setOnClickListener(v -> {
            HyperFoodApplication.menuTitle = getString(R.string.recommend_food);
            HyperFoodApplication.isRecommend = true;
            startActivity(new Intent(getContext(), SelectFoodTypeActivity.class));
            overridePendingTransitionEnter();
        });
        binding.btnSave.setOnClickListener(v -> {
            HyperFoodApplication.menuTitle = getString(R.string.all_food);
            HyperFoodApplication.isRecommend = false;
            startActivity(new Intent(getContext(), SelectFoodTypeActivity.class));
            overridePendingTransitionEnter();
        });
        binding.progressCabo.setOnClickListener(v -> openEatDetail());
        binding.progressKcal.setOnClickListener(v -> openEatDetail());
        binding.progressSodium.setOnClickListener(v -> openEatDetail());
    }

    protected void selectDate(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(date -> {
            binding.tvDate.setText(date);
            getFood();
        });
        datePickerFragment.show(getFragmentManager(), "");
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.back_again, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void openEatDetail() {
        Intent intent = new Intent(getContext(), EatDetailActivity.class);
        intent.putExtra(EatDetailActivity.EXTRA_DATE, binding.tvDate.getText().toString());
        startActivity(intent);
        overridePendingTransitionEnter();
    }

    private void getFood(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference(USER_FOOD)
                .child(user.getUid())
                .child(DateTimeUtils.getDateSaveFood(binding.tvDate.getText().toString()));
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float sodium = 0;
                float cabo = 0;
                float kcal = 0;

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_morning)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    sodium += Float.parseFloat(food.getSoduim());
                    cabo += Float.parseFloat(food.getCarbohydrate());
                    kcal += Float.parseFloat(food.getKcal());
                }

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_noon)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    sodium += Float.parseFloat(food.getSoduim());
                    cabo += Float.parseFloat(food.getCarbohydrate());
                    kcal += Float.parseFloat(food.getKcal());
                }

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_evening)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    sodium += Float.parseFloat(food.getSoduim());
                    cabo += Float.parseFloat(food.getCarbohydrate());
                    kcal += Float.parseFloat(food.getKcal());
                }

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_night)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    sodium += Float.parseFloat(food.getSoduim());
                    cabo += Float.parseFloat(food.getCarbohydrate());
                    kcal += Float.parseFloat(food.getKcal());
                }

                setProgress(binding.progressCabo, cabo);
                setProgress(binding.progressKcal, kcal);
                setProgress(binding.progressSodium, sodium);
                updateValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                setProgress(binding.progressCabo, 0);
                setProgress(binding.progressKcal, 0);
                setProgress(binding.progressSodium, 0);
                updateValue();
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        };
        foodRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void setMaxProgress(){
        binding.progressCabo.setMax(getResources().getInteger(R.integer.carbohydrate_day));
        binding.progressSodium.setMax(getResources().getInteger(R.integer.sodium_day));
        binding.progressKcal.setMax(HPF.getInstance().getUser().getGender().equals(getString(R.string.male)) ?
                getResources().getInteger(R.integer.male_kcal_day) :
                getResources().getInteger(R.integer.female_kcal_day));
    }

    private void setProgress(RoundCornerProgressBar progressBar, float progress){
        progressBar.setProgress(progress);
    }

    private void updateValue(){
        DecimalFormat df = new DecimalFormat("#.##");
        binding.caboValue.setText(String.format("%s/%.0f %s", df.format(binding.progressCabo.getProgress()), binding.progressCabo.getMax(), getString(R.string.carbohydrate)));
        binding.sodiumValue.setText(String.format("%s/%.0f %s", df.format(binding.progressSodium.getProgress()), binding.progressSodium.getMax(), getString(R.string.mili_gram)));
        binding.kcalValue.setText(String.format("%s/%.0f %s", df.format(binding.progressKcal.getProgress()), binding.progressKcal.getMax(), getString(R.string.kilo_cal)));
    }
}
