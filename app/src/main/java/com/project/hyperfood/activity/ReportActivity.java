package com.project.hyperfood.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
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


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import static com.project.hyperfood.application.HyperFoodApplication.USER_FOOD;

public class ReportActivity extends AbstractActivity implements OnChartValueSelectedListener {

    private ActivityReportBinding binding;
    private float currentValue = 0;
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
        configChart();
        getFood();
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
            startActivity(new Intent(getContext(), SelectFoodTypeActivity.class));
            overridePendingTransitionEnter();
        });
        binding.btnSave.setOnClickListener(v -> {
            HyperFoodApplication.menuTitle = getString(R.string.all_food);
            startActivity(new Intent(getContext(), SelectFoodTypeActivity.class));
            overridePendingTransitionEnter();
        });
    }

    private void configChart(){
        binding.chartView.setUsePercentValues(true);
        binding.chartView.getDescription().setEnabled(false);
        binding.chartView.setExtraOffsets(5, 10, 5, 5);

        binding.chartView.setDragDecelerationFrictionCoef(0.95f);
        binding.chartView.setCenterTextTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        binding.chartView.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        binding.chartView.setDrawHoleEnabled(true);
        binding.chartView.setHoleColor(Color.WHITE);

        binding.chartView.setTransparentCircleColor(Color.WHITE);
        binding.chartView.setTransparentCircleAlpha(110);

        binding.chartView.setHoleRadius(17f);
        binding.chartView.setTransparentCircleRadius(20f);

        binding.chartView.setDrawCenterText(true);

        binding.chartView.setRotationAngle(0);
        // enable rotation of the chart by touch
        binding.chartView.setRotationEnabled(true);
        binding.chartView.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        binding.chartView.setOnChartValueSelectedListener(this);

        binding.chartView.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = binding.chartView.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    private void setData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(getLimit(), "เหมาสม"));
        entries.add(new PieEntry(currentValue, "ไม่เหมาะสม"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add( Color.rgb(3, 169, 244));
        colors.add( Color.rgb(252, 92, 39));
        dataSet.setColors(colors);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setUsingSliceColorAsValueLineColor(true);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        data.setValueTextSize(18);
        data.setValueFormatter(new PercentFormatter());
        data.setDrawValues(false);
        binding.chartView.setData(data);
        // undo all highlights
        binding.chartView.highlightValues(null);

        binding.chartView.invalidate();
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

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void getFood(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference(USER_FOOD)
                .child(user.getUid())
                .child(DateTimeUtils.getDateSaveFood(binding.tvDate.getText().toString()));
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentValue = 0;

                List<Food> morningFood = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_morning)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    morningFood.add(food);
                    currentValue += getFoodValue(food);
                }

                List<Food> noonFood = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_noon)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    noonFood.add(food);
                    currentValue += getFoodValue(food);
                }

                List<Food> eveningFood = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_evening)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    eveningFood.add(food);
                    currentValue += getFoodValue(food);
                }

                List<Food> nightFood = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.child(getString(R.string.txt_night)).getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    nightFood.add(food);
                    currentValue += getFoodValue(food);
                }

                setData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                currentValue = 0;
                setData();
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        };
        foodRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private int getLimit(){
        if (HPF.getInstance().getUser().getCongenitalDisease().equals(getContext().getString(R.string.hypertension))){
            return getResources().getInteger(R.integer.sodium);
        }else if (HPF.getInstance().getUser().getCongenitalDisease().equals(getContext().getString(R.string.obesity))){
            return getResources().getInteger(R.integer.fat);
        }else {
            return getResources().getInteger(R.integer.carbohydrate);
        }
    }

    private float getFoodValue(Food food){
        if (HPF.getInstance().getUser().getCongenitalDisease().equals(getContext().getString(R.string.hypertension))){
            return Float.parseFloat(food.getSoduim());
        }else if (HPF.getInstance().getUser().getCongenitalDisease().equals(getContext().getString(R.string.obesity))){
            return Float.parseFloat(food.getFat());
        }else {
            return Float.parseFloat(food.getCarbohydrate());
        }
    }
}
