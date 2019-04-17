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
import com.project.hyperfood.R;
import com.project.hyperfood.application.HyperFoodApplication;
import com.project.hyperfood.common.utils.DatePickerFragment;
import com.project.hyperfood.common.utils.DateTimeUtils;
import com.project.hyperfood.common.utils.FontUtil;
import com.project.hyperfood.databinding.ActivityReportBinding;


import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class ReportActivity extends AbstractActivity implements OnChartValueSelectedListener {

    private ActivityReportBinding binding;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);

        binding.tvAppName.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON));
        binding.btnRecommend.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON));
        binding.btnSave.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON));
        binding.tvDate.setText(DateTimeUtils.getCurrentDate());

        setClickEvent();
        configChart();
        setData();
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
        binding.chartView.setCenterTextTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON));
        binding.chartView.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        binding.chartView.setDrawHoleEnabled(true);
        binding.chartView.setHoleColor(Color.WHITE);

        binding.chartView.setTransparentCircleColor(Color.WHITE);
        binding.chartView.setTransparentCircleAlpha(110);

        binding.chartView.setHoleRadius(58f);
        binding.chartView.setTransparentCircleRadius(61f);

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
        entries.add(new PieEntry(20, "เหมาสม"));
        entries.add(new PieEntry(50, "ไม่เหมาะสม"));

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
        data.setValueTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON));
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
        datePickerFragment.setListener(date -> binding.tvDate.setText(date));
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
}
