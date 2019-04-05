package com.project.hyperfood.activity;

import android.os.Bundle;

import com.project.hyperfood.R;
import com.project.hyperfood.databinding.ActivityLoginBinding;

import androidx.databinding.DataBindingUtil;

public class LoginActivity extends AbstractActivity{

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    }

}
