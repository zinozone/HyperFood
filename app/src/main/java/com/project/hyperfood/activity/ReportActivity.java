package com.project.hyperfood.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import com.project.hyperfood.common.dialog.AlertDangerDialog;
import com.project.hyperfood.common.dialog.HowToDialog;
import com.project.hyperfood.common.model.Food;
import com.project.hyperfood.common.preferences.HPF;
import com.project.hyperfood.common.utils.DatePickerFragment;
import com.project.hyperfood.common.utils.DateTimeUtils;
import com.project.hyperfood.common.utils.FontUtil;
import com.project.hyperfood.databinding.ActivityReportBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import java.text.DecimalFormat;

import static com.project.hyperfood.application.HyperFoodApplication.USER_FOOD;

public class ReportActivity extends AbstractActivity{
    public static final String PREF_NAME = "alert-pref";

    private ActivityReportBinding binding;
    private boolean doubleBackToExitPressedOnce = false;
    private  float sodium = 0;
    private float carbohydrate = 0;
    private float kcal = 0;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);
        preferences = getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        binding.tvAppName.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        binding.btnRecommend.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        binding.btnSave.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        binding.tvDate.setText(DateTimeUtils.getCurrentDate());
        binding.redDot.setVisibility(preferences.getBoolean(DateTimeUtils.getPrefRedDot(), false) ? View.VISIBLE:View.GONE);

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
        binding.btnNotification.setOnClickListener(v -> {
            binding.redDot.setVisibility(View.GONE);
            setPrefAlert(DateTimeUtils.getPrefRedDot(), false);
            startActivity(new Intent(getContext(), NotificationActivity.class));
            overridePendingTransitionEnter();
        });
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
                sodium = 0;
                carbohydrate = 0;
                kcal = 0;

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_morning)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    sodium += Float.parseFloat(food.getSoduim());
                    carbohydrate += Float.parseFloat(food.getCarbohydrate());
                    kcal += Float.parseFloat(food.getKcal());
                }

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_noon)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    sodium += Float.parseFloat(food.getSoduim());
                    carbohydrate += Float.parseFloat(food.getCarbohydrate());
                    kcal += Float.parseFloat(food.getKcal());
                }

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_evening)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    sodium += Float.parseFloat(food.getSoduim());
                    carbohydrate += Float.parseFloat(food.getCarbohydrate());
                    kcal += Float.parseFloat(food.getKcal());
                }

                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_night)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    sodium += Float.parseFloat(food.getSoduim());
                    carbohydrate += Float.parseFloat(food.getCarbohydrate());
                    kcal += Float.parseFloat(food.getKcal());
                }

                setProgress(binding.progressCabo, carbohydrate);
                setProgress(binding.progressKcal, kcal);
                setProgress(binding.progressSodium, sodium);
                updateValue();
                checkValue();
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
        binding.caboValue.setText(String.format("%s/%.0f %s", df.format(carbohydrate), binding.progressCabo.getMax(), getString(R.string.carbohydrate)));
        binding.sodiumValue.setText(String.format("%s/%.0f %s", df.format(sodium), binding.progressSodium.getMax(), getString(R.string.mili_gram)));
        binding.kcalValue.setText(String.format("%s/%.0f %s", df.format(kcal), binding.progressKcal.getMax(), getString(R.string.kilo_cal)));
    }

    private void checkValue(){
        int maxKcal = HPF.getInstance().getUser().getGender().equals(getString(R.string.male)) ? getResources().getInteger(R.integer.male_kcal_day) : getResources().getInteger(R.integer.female_kcal_day);
        if (sodium > getResources().getInteger(R.integer.sodium_day) && !preferences.getBoolean(DateTimeUtils.getPrefSodium(), false)){
            setPrefAlert(DateTimeUtils.getPrefSodium(), true);
            showAlertDialog(AlertDangerDialog.ALERT_SODIUM);
        }else if (carbohydrate > getResources().getInteger(R.integer.carbohydrate_day) && !preferences.getBoolean(DateTimeUtils.getPrefCarbohydrate(), false)){
            setPrefAlert(DateTimeUtils.getPrefCarbohydrate(), true);
            showAlertDialog(AlertDangerDialog.ALERT_CARBOHYDRATE);
        }else if (kcal > maxKcal && !preferences.getBoolean(DateTimeUtils.getPrefKcal(), false)){
            setPrefAlert(DateTimeUtils.getPrefKcal(), true);
            showAlertDialog(AlertDangerDialog.ALERT_KCAL);
        }
    }

    private void showAlertDialog(int key){
        binding.redDot.setVisibility(View.VISIBLE);
        setPrefAlert(DateTimeUtils.getPrefRedDot(), true);
        AlertDangerDialog dialog = new AlertDangerDialog(getContext());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(AlertDangerDialog.ALERT_KEY, key);
        dialog.setArguments(bundle);
        dialog.setDialogClick(new onDialogClick() {
            @Override
            public void onHowToClick() {
                dialog.dismiss();
                showHowToDialog(key);
            }

            @Override
            public void onDismiss() {
                dialog.dismiss();
            }
        });
        dialog.show(fragmentTransaction, AlertDangerDialog.TAG);
    }

    private void showHowToDialog(int key){
        HowToDialog dialog = new HowToDialog(getContext());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(AlertDangerDialog.ALERT_KEY, key);
        dialog.setArguments(bundle);
        dialog.show(fragmentTransaction, HowToDialog.TAG);
    }

    private void setPrefAlert(String prefName, boolean isBoolean){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(prefName, isBoolean);
        editor.apply();
    }

    public interface onDialogClick{
        void onHowToClick();
        void onDismiss();
    }
}
