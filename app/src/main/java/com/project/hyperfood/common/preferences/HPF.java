package com.project.hyperfood.common.preferences;

import android.content.Context;

import com.project.hyperfood.common.model.User;


public class HPF implements UserPreference {

    private static HPF instance;
    private UserPreference userPreference;

    private HPF() {
    }

    public static HPF getInstance() {
        if (instance == null) {
            instance = new HPF();
        }
        return instance;
    }

    public void init(Context context) {
        userPreference = new UserPreferenceImpl(context);
    }

    @Override
    public User getUser() {
        return userPreference.getUser();
    }

    @Override
    public void setUser(User user) {
        userPreference.setUser(user);
    }

    @Override
    public void clear() {
        userPreference.clear();
    }
}
