package com.project.hyperfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.hyperfood.R;
import com.project.hyperfood.common.model.Food;
import com.project.hyperfood.common.utils.DateTimeUtils;
import com.project.hyperfood.databinding.ActivityFoodBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import static com.project.hyperfood.application.HyperFoodApplication.USER_FOOD;
import static com.project.hyperfood.common.utils.DateTimeUtils.EVENING;
import static com.project.hyperfood.common.utils.DateTimeUtils.MORNING;
import static com.project.hyperfood.common.utils.DateTimeUtils.NIGHT;
import static com.project.hyperfood.common.utils.DateTimeUtils.NOON;

public class FoodActivity extends AbstractActivity{

    public static final String EXTRA_FOOD = "extra-food";

    private ActivityFoodBinding binding;
    private Food food;
    private int dayTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food);
        food = getIntent().getParcelableExtra(EXTRA_FOOD);
        if (food != null){
            setData();
        }

        setClickEvent();
        setDayTime();
    }

    private void setData(){
        binding.tvFoodName.setText(food.getFoodName());
        binding.tvGrams.setText(food.getGrams() + " " + getString(R.string.grams));
        binding.tvSodiumGrams.setText(food.getSoduim() + " " + getString(R.string.mili_grams));
        binding.tvFatGrams.setText(food.getFat() + " " + getString(R.string.grams));
        binding.tvCaboGrams.setText(food.getCarbohydrate() + " " + getString(R.string.grams));
        binding.tvSugarGrams.setText(food.getSugars() + " " + getString(R.string.grams));
        binding.tvProteinGrams.setText(food.getProtein() + " " + getString(R.string.grams));
    }

    private void setClickEvent(){
        binding.btnMorning.setOnClickListener(v -> setMorning());
        binding.btnNoon.setOnClickListener(v -> setNoon());
        binding.btnEvening.setOnClickListener(v -> setEvening());
        binding.btnNight.setOnClickListener(v -> setNight());
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.tvMorning.setOnClickListener(v -> setMorning());
        binding.tvNoon.setOnClickListener(v -> setNoon());
        binding.tvEvening.setOnClickListener(v -> setEvening());
        binding.tvnight.setOnClickListener(v -> setNight());
        binding.btnSave.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            addFood(user);
        });
        binding.btnCancel.setOnClickListener(v -> openReportActivity());
    }

    private void setDayTime(){
        switch (DateTimeUtils.getDayTime()){
            case MORNING:
                setMorning();
                break;
            case NOON:
                setNoon();
                break;
            case EVENING:
                setEvening();
                break;
            case NIGHT:
                setNight();
                break;
            default:
                setMorning();
        }
    }

    private void clearResource(){
        binding.btnMorning.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_morning_black));
        binding.btnNoon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_noon_black));
        binding.btnEvening.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sunrise_black));
        binding.btnNight.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_moon_black));
    }

    private void setMorning(){
        dayTime = MORNING;
        clearResource();
        binding.btnMorning.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_morning));
    }

    private void setNoon(){
        dayTime = NOON;
        clearResource();
        binding.btnNoon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_noon));
    }

    private void setEvening(){
        dayTime = EVENING;
        clearResource();
        binding.btnEvening.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sunrise));
    }

    private void setNight(){
        dayTime = NIGHT;
        clearResource();
        binding.btnNight.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_moon));
    }

    private void addFood(FirebaseUser user){
        DatabaseReference userFoodRef = FirebaseDatabase.getInstance().getReference(USER_FOOD)
                .child(user.getUid())
                .child(DateTimeUtils.getDateSaveFood())
                .child(getDayTime())
                .child(DateTimeUtils.getTimeSaveFood());
        userFoodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);
                if (food != null){
                    Toast.makeText(getContext(), R.string.save_success, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), R.string.save_fail, Toast.LENGTH_SHORT).show();
                }
                openReportActivity();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), R.string.save_fail, Toast.LENGTH_SHORT).show();
            }
        });
        userFoodRef.setValue(getFood());
    }

    private Food getFood(){
        food.setDayTime(dayTime);
        food.setServing("1");
        return food;
    }

    private void openReportActivity(){
        Intent intent = new Intent(getContext(), ReportActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private String getDayTime(){
        switch (dayTime){
            case MORNING:
                return getString(R.string.txt_morning);
            case NOON:
                return getString(R.string.txt_noon);
            case EVENING:
                return getString(R.string.txt_evening);
            case NIGHT:
                return getString(R.string.txt_night);
            default:
                return getString(R.string.txt_morning);
        }
    }
}
