package com.project.hyperfood.common.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.hyperfood.common.utils.UriAdapter;

public class SimplePreference {

    private SharedPreferences preference;

    /**
     * Instantiates a new Simple preference.
     *
     * @param context the context
     * @param name    the name
     */
    public SimplePreference(Context context, String name) {
        preference = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * Gets string.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the string
     */
    public String getString(String key, String defaultValue) {
        return preference.getString(key, defaultValue);
    }

    /**
     * Sets string.
     *
     * @param key   the key
     * @param value the value
     */
    public void setString(String key, String value) {
        preference.edit().putString(key, value).apply();
    }

    /**
     * Sets boolean.
     *
     * @param key   the key
     * @param value the value
     */
    public void setBoolean(String key, Boolean value) {
        preference.edit().putBoolean(key, value).apply();
    }

    /**
     * Gets boolean.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the boolean
     */
    public Boolean getBoolean(String key, Boolean defaultValue) {
        return preference.getBoolean(key, defaultValue);
    }

    /**
     * Sets integer.
     *
     * @param key   the key
     * @param value the value
     */
    public void setInteger(String key, Integer value) {
        preference.edit().putInt(key, value).apply();
    }


    /**
     * Gets integer.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the integer
     */
    public Integer getInteger(String key, Integer defaultValue) {
        return preference.getInt(key, defaultValue);
    }


    /**
     * Sets object.
     *
     * @param key    the key
     * @param object the object
     */
    public void setObject(String key, Object object) {
        preference.edit().putString(key, getGson().toJson(object)).apply();
    }

    /**
     * Gets long.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the long
     */
    public Long getLong(String key, Long defaultValue) {
        return preference.getLong(key, defaultValue);
    }


    /**
     * Sets long.
     *
     * @param key   the key
     * @param value the value
     */
    public void setLong(String key, Long value) {
        preference.edit().putLong(key, value).apply();
    }

    /**
     * Gets object.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param clazz the clazz
     * @return the object
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String value = preference.getString(key, null);
        if (!TextUtils.isEmpty(value)) {
            try {
                return getGson().fromJson(value, clazz);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    private Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Uri.class, new UriAdapter());
        return gsonBuilder.create();
    }

    /**
     * Clear.
     */
    public void clear() {
        preference.edit().clear().apply();
    }

}
