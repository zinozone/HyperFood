package com.project.hyperfood.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.project.hyperfood.R;
import com.project.hyperfood.databinding.ActivityEatDetailBinding;

public class EatDetailActivity extends AbstractActivity{

    public static final String EXTRA_DATE = "extra-date";

    private ActivityEatDetailBinding binding;
    private String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_eat_detail);

        date = getIntent().getStringExtra(EXTRA_DATE);
    }
}
