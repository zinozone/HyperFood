package com.project.hyperfood.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.hyperfood.R;
import com.project.hyperfood.common.model.User;
import com.project.hyperfood.common.preferences.HPF;
import com.project.hyperfood.databinding.ActivitySplashScreenBinding;
import com.project.hyperfood.common.utils.FontUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import static com.project.hyperfood.application.HyperFoodApplication.USER;

public class SplashScreenActivity extends AppCompatActivity implements  Animator.AnimatorListener {

    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        binding.tvAppName.setTypeface(FontUtil.getFont(getAssets(), FontUtil.LAMMOON_BOLD));
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
            getUser(user.getUid());
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

    private void getUser(String uid){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(USER).child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null){
                    HPF.getInstance().setUser(user);
                    openReportPage();
                }else {
                    openMainPage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                openMainPage();
            }

        };
        userRef.addListenerForSingleValueEvent(valueEventListener);
    }
}
