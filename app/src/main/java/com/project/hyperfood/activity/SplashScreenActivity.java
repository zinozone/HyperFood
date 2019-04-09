package com.project.hyperfood.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.hyperfood.R;
import com.project.hyperfood.databinding.ActivitySplashScreenBinding;
import com.project.hyperfood.utils.FontUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class SplashScreenActivity extends AppCompatActivity implements  Animator.AnimatorListener {

    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        binding.tvAppName.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON));
        binding.animationView.addAnimatorListener(this);
        binding.animationView.playAnimation();

    }

    @Override
    public void onAnimationStart(Animator animation) { }

    @Override
    public void onAnimationEnd(Animator animation) {
       checkLogin();
    }

    @Override
    public void onAnimationCancel(Animator animation) { }

    @Override
    public void onAnimationRepeat(Animator animation) { }

    private void checkLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            openMainPage();
        }else {
            openReportPage();
        }
    }

    private void openMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void openReportPage(){
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
        finish();
    }
}
