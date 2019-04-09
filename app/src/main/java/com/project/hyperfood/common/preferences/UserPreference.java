package com.project.hyperfood.common.preferences;

import com.project.hyperfood.common.model.User;

public interface UserPreference {
    User getUser();
    void setUser(User user);
    void clear();
}
