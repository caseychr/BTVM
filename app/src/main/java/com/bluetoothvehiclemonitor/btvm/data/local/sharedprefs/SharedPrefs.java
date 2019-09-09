package com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefs {
    private static final String TAG = "SharedPrefs";

    private static SharedPrefs instance;

    public static final String PREF_BT_DEVICE_NAME = "PREF_BT_DEVICE_NAME";
    private static final String PREF_BT_DEVICE_ADDRESS = "PREF_BT_DEVICE_ADDRESS";
    private static final String PREF_LAST_KNOWN_LAT = "PREF_LAST_KNOWN_LAT";
    private static final String PREF_LAST_KNOWN_LON = "PREF_LAST_KNOWN_LON";
    private static final String PREF_BT_DEVICE_CONNECTED = "PREF_BT_DEVICE_CONNECTED";
    private static final String PREF_METRIC = "PREF_METRIC";

    public SharedPreferences mSharedPrefs;
    public SharedPreferences.Editor mEditor;

    public static SharedPrefs getInstance(Context context) {
        if(instance == null) {
            instance = new SharedPrefs(context);
        }
        return instance;
    }

    private SharedPrefs(Context context) {
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String[] getDevice() {
        String[] device = new String[2];
        device[0] = mSharedPrefs.getString(PREF_BT_DEVICE_NAME, null);
        device[1] = mSharedPrefs.getString(PREF_BT_DEVICE_ADDRESS, null);
        return device;
    }

    public void setDevice(String deviceName, String deviceAddress) {
        mEditor = mSharedPrefs.edit();
        mEditor.putString(PREF_BT_DEVICE_NAME, deviceName);
        mEditor.putString(PREF_BT_DEVICE_ADDRESS, deviceAddress);
        mEditor.commit();
    }

    public String[] getLastLatLon() {
        String[] lastLatLon = new String[2];
        lastLatLon[0] = mSharedPrefs.getString(PREF_LAST_KNOWN_LAT, null);
        lastLatLon[1] = mSharedPrefs.getString(PREF_LAST_KNOWN_LON, null);
        return lastLatLon;
    }

    public void setLatLon(String lat, String lon) {
        mEditor = mSharedPrefs.edit();
        mEditor.putString(PREF_LAST_KNOWN_LAT, lat);
        mEditor.putString(PREF_LAST_KNOWN_LON, lon);
        mEditor.commit();
    }

    public boolean getDeviceIsConnected() {
        return mSharedPrefs.getBoolean(PREF_BT_DEVICE_CONNECTED, false);
    }

    public void setDeviceIsConnected(boolean isConnected) {
        mEditor = mSharedPrefs.edit();
        mEditor.putBoolean(PREF_BT_DEVICE_CONNECTED, isConnected);
        mEditor.commit();
    }

    public boolean getIsMetric() {
        return mSharedPrefs.getBoolean(PREF_METRIC, false);
    }

    public void setIsMetric(boolean isMetric) {
        mEditor = mSharedPrefs.edit();
        mEditor.putBoolean(PREF_METRIC, isMetric);
        mEditor.commit();
    }
}
