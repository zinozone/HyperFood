package com.project.hyperfood.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.project.hyperfood.R;
import com.project.hyperfood.common.preferences.HPF;
import com.project.hyperfood.databinding.ActivityProfileBinding;

public class ProfileActivity extends AbstractActivity{

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        binding.btnBack.setOnClickListener(v -> onBackPressed());
        setData();
    }

    private void setData(){
        binding.inputEmail.setText(HPF.getInstance().getUser().getEmail());
        binding.inputFirstName.setText(HPF.getInstance().getUser().getFirstName());
        binding.inputLastName.setText(HPF.getInstance().getUser().getLastName());
        binding.inputDisease.setText(HPF.getInstance().getUser().getCongenitalDisease());
        binding.inputGender.setText(HPF.getInstance().getUser().getGender());
        binding.inputAge.setText(HPF.getInstance().getUser().getAge());
    }
}
