package com.project.hyperfood.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.project.hyperfood.R;
import com.project.hyperfood.common.preferences.HPF;
import com.project.hyperfood.common.utils.FontUtil;
import com.project.hyperfood.databinding.ActivitySettingsBinding;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class SettingActivity extends AbstractActivity{

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        binding.tvSetting.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.btnSignOut.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            HPF.getInstance().clear();
            startActivity(new Intent(getContext(), SplashScreenActivity.class));
            finish();
        });
        binding.btnUser.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ProfileActivity.class));
            overridePendingTransitionEnter();
        });
    }
}
