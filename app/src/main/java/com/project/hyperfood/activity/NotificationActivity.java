package com.project.hyperfood.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.project.hyperfood.R;
import com.project.hyperfood.databinding.ActivityNotificationBinding;

public class NotificationActivity extends AbstractActivity{

    private ActivityNotificationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

    }
}
