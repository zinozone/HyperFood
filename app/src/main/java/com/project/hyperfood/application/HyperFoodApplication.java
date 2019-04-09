package com.project.hyperfood.application;

import android.app.Application;

import com.project.hyperfood.common.preferences.HPF;


public class HyperFoodApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        HPF.getInstance().init(this);
    }
}
