package com.project.hyperfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.hyperfood.R;
import com.project.hyperfood.common.model.User;
import com.project.hyperfood.common.preferences.HPF;
import com.project.hyperfood.databinding.ActivityLoginBinding;
import com.project.hyperfood.common.utils.EmailUtil;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import static com.project.hyperfood.application.HyperFoodApplication.USER;

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
                        if (task.isSuccessful()) {
                            getUser(mAuth.getUid());
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), R.string.validate_register, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getUser(String uid){
        DatabaseReference diseaseRef = FirebaseDatabase.getInstance().getReference(USER).child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                User user = dataSnapshot.getValue(User.class);
                if (user != null){
                    HPF.getInstance().setUser(user);
                    openReportPage();
                }else {
                    Toast.makeText(getContext(), R.string.validate_register, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), R.string.validate_register, Toast.LENGTH_SHORT).show();
            }

        };
        diseaseRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void openReportPage(){
        startActivity(new Intent(getContext(), ReportActivity.class));
        overridePendingTransitionEnter();
        finish();
    }

}
