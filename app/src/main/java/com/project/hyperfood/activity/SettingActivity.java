package com.project.hyperfood.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.project.hyperfood.R;
import com.project.hyperfood.common.preferences.HPF;
import com.project.hyperfood.common.utils.FontUtil;
import com.project.hyperfood.databinding.ActivitySettingsBinding;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import static com.project.hyperfood.activity.ReportActivity.PREF_NAME;

public class SettingActivity extends AbstractActivity{

    private ActivitySettingsBinding binding;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        preferences = getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        binding.tvSetting.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.btnSignOut.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            HPF.getInstance().clear();
            preferences.edit().clear().apply();
            startActivity(new Intent(getContext(), SplashScreenActivity.class));
            finish();
        });
        binding.btnUser.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ProfileActivity.class));
            overridePendingTransitionEnter();
        });
        binding.btnTutorial.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), TutorialActivity.class));
            overridePendingTransitionEnter();
        });
    }
}
