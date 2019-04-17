package com.project.hyperfood.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.project.hyperfood.R;
import com.project.hyperfood.databinding.ActivityMainBinding;
import com.project.hyperfood.common.utils.FontUtil;

public class MainActivity extends AbstractActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.tvAppName.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));

        binding.btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
            overridePendingTransitionEnter();
        });

        binding.btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), RegisterActivity.class));
            overridePendingTransitionEnter();
        });
    }
}
