package com.project.hyperfood.activity;

import android.os.Bundle;

import com.project.hyperfood.R;
import com.project.hyperfood.databinding.ActivityRegisterBinding;

import androidx.databinding.DataBindingUtil;

public class RegisterActivity extends AbstractActivity{

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

    }

}
