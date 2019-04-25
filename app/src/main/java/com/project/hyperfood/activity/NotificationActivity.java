package com.project.hyperfood.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.project.hyperfood.R;
import com.project.hyperfood.common.dialog.AlertDangerDialog;
import com.project.hyperfood.common.dialog.HowToDialog;
import com.project.hyperfood.common.utils.DateTimeUtils;
import com.project.hyperfood.databinding.ActivityNotificationBinding;

import static com.project.hyperfood.activity.ReportActivity.PREF_NAME;

public class NotificationActivity extends AbstractActivity{

    private ActivityNotificationBinding binding;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

        preferences = getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        binding.btnCarbohydrate.setVisibility(preferences.getBoolean(DateTimeUtils.getPrefCarbohydrate(), false) ? View.VISIBLE:View.GONE);
        binding.btnKcal.setVisibility(preferences.getBoolean(DateTimeUtils.getPrefKcal(), false) ? View.VISIBLE:View.GONE);
        binding.btnSodium.setVisibility(preferences.getBoolean(DateTimeUtils.getPrefSodium(), false) ? View.VISIBLE:View.GONE);

        binding.btnCarbohydrate.setOnClickListener(v -> showHowToDialog(AlertDangerDialog.ALERT_CARBOHYDRATE));
        binding.btnKcal.setOnClickListener(v -> showHowToDialog(AlertDangerDialog.ALERT_KCAL));
        binding.btnSodium.setOnClickListener(v -> showHowToDialog(AlertDangerDialog.ALERT_SODIUM));

    }

    private void showHowToDialog(int key){
        HowToDialog dialog = new HowToDialog(getContext());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(AlertDangerDialog.ALERT_KEY, key);
        dialog.setArguments(bundle);
        dialog.show(fragmentTransaction, HowToDialog.TAG);
    }
}
