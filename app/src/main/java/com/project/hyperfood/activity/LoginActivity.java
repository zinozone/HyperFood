package com.project.hyperfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.hyperfood.R;
import com.project.hyperfood.databinding.ActivityLoginBinding;
import com.project.hyperfood.utils.EmailUtil;

import androidx.databinding.DataBindingUtil;

public class LoginActivity extends AbstractActivity{

    private ActivityLoginBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        binding.btnLogin.setOnClickListener(v -> validate());
        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void validate(){
        if (
                !EmailUtil.isEmailValid(binding.inputEmail.getText().toString().trim())
                        || TextUtils.isEmpty(binding.inputEmail.getText().toString().trim())
                        || TextUtils.isEmpty(binding.inputPassword.getText().toString().trim())
        ){
            Toast.makeText(getContext(), R.string.validate_register, Toast.LENGTH_SHORT).show();
        }else {
            login();
        }
    }

    private void login(){
        createProgressDialog(getString(R.string.loading_login));
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(binding.inputEmail.getText().toString().trim(), binding.inputPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                           openReportPage();
                        }else {
                            Toast.makeText(getContext(), R.string.validate_register, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void openReportPage(){
        startActivity(new Intent(getContext(), ReportActivity.class));
        overridePendingTransitionEnter();
        finish();
    }

}
