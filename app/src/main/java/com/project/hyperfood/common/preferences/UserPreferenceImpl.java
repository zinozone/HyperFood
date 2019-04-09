package com.project.hyperfood.common.preferences;

import android.content.Context;

import com.project.hyperfood.common.model.User;

public class UserPreferenceImpl implements UserPreference{

    public static final String USER_PREFER = "HPF_PREFS";
    private static final String USER_KEY = "user-key";

    private final SimplePreference pref;
    private User user;

    public UserPreferenceImpl(Context context) {
        this.pref = new SimplePreference(context, USER_PREFER);
    }

    @Override
    public User getUser() {
        if (user == null) {
            user = pref.getObject(USER_KEY, User.class);
        }
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        pref.setObject(USER_KEY, user);
    }

    @Override
    public void clear() {
        user = null;
        pref.clear();
    }
}
