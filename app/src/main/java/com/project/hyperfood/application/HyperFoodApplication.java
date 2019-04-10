package com.project.hyperfood.application;

import android.app.Application;

import com.project.hyperfood.common.preferences.HPF;


public class HyperFoodApplication extends Application {

    public static final String USER = "users";
    public static final String DISEASE = "congenitalDisease";

    @Override
    public void onCreate() {
        super.onCreate();

        HPF.getInstance().init(this);
    }
}
