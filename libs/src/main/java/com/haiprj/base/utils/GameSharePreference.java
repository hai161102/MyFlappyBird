package com.haiprj.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class GameSharePreference {
    private static final String SHARE_PREF_NAME = "setting_app.pref";
    @SuppressLint("StaticFieldLeak")
    private static GameSharePreference instance;
    private Context context;
    private SharedPreferences mSharedPreferences;

    public static GameSharePreference getInstance() {
        if (instance == null) {
            instance = new GameSharePreference();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        mSharedPreferences = context.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);

    }

    private GameSharePreference() {

    }


    @SuppressWarnings("unchecked")
    public  <T> T get(String key, Object def, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) mSharedPreferences.getString(key, (String) def);
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, (Boolean) def));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, (Float) def));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, (Integer) def));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, (Long) def));
        } else {
            return (T) new Gson()
                    .fromJson(mSharedPreferences.getString(key, (String) def), anonymousClass);
        }
    }

    public boolean getBoolean(String key, boolean def) {
        return mSharedPreferences.getBoolean(key, def);
    }

    public String getString(String key, String  def) {
        return mSharedPreferences.getString(key, def);
    }

    public int getInt(String key, int def) {
        return mSharedPreferences.getInt(key, def);

    }

    public long getLong(String key, long def) {
        return mSharedPreferences.getLong(key, def);
    }


    public float getFloat(String key, float def) {
        return mSharedPreferences.getFloat(key, def);

    }


    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        } else {
            editor.putString(key, new Gson().toJson(data));
        }
        editor.apply();
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }


    public void setBoolean(String key, boolean b) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public void setFloat(String key, float value){
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void setInt(String key, int value) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void setString(String key, String value) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
