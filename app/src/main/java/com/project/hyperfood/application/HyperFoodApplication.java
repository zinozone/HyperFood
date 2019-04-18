package com.project.hyperfood.application;

import android.app.Application;

import com.project.hyperfood.common.preferences.HPF;


public class HyperFoodApplication extends Application {

    public static final String USER = "users";
    public static final String DISEASE = "congenitalDisease";
    public static final String FOOD_TYPE = "foodType";
    public static final String FOOD = "food";
    public static final String USER_FOOD = "userFood";
    public static String menuTitle;

    @Override
    public void onCreate() {
        super.onCreate();

        HPF.getInstance().init(this);
    }
}
