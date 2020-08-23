package com.shrikanthravi.customnavigationdrawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

@SuppressLint("CommitPrefEdits")
public class Preference {
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;

    public static String DEVICE_DISPLAY_WIDTH = "DEVICE_DISPLAY_WIDTH";
    public static String DEVICE_DISPLAY_HEIGHT = "DEVICE_DISPLAY_HEIGHT";
    public static String SHARING_COUNT = "SHARING_COUNT";


    public Preference(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        edit = preferences.edit();
    }

    public void saveStringInPreference(String strKey, String strValue) {
        edit.putString(strKey, strValue);
    }

    public void saveIntInPreference(String strKey, int value) {
        edit.putInt(strKey, value);
    }

    public void saveBooleanInPreference(String strKey, boolean value) {
        edit.putBoolean(strKey, value);
    }

    public void saveLongInPreference(String strKey, Long value) {
        edit.putLong(strKey, value);
    }

    public void saveDoubleInPreference(String strKey, String value) {
        edit.putString(strKey, value);
    }

    public void removeFromPreference(String strKey) {
        edit.remove(strKey);
    }

    public void commitPreference() {
        edit.commit();
    }

    public String getStringFromPreference(String strKey, String defaultValue) {
        return preferences.getString(strKey, defaultValue);
    }

    public boolean getbooleanFromPreference(String strKey, boolean defaultValue) {
        return preferences.getBoolean(strKey, defaultValue);
    }

    public int getIntFromPreference(String strKey, int defaultValue) {
        return preferences.getInt(strKey, defaultValue);
    }

    public double getDoubleFromPreference(String strKey, double defaultValue) {
        return Double.parseDouble(preferences.getString(strKey, "" + defaultValue));
    }

    public long getLongInPreference(String strKey) {
        return preferences.getLong(strKey, 0);
    }
}
