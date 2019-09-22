package com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPrefs {
    private static final String TAG = "SharedPrefs";

    private static SharedPrefs instance;

    public static final String PREF_BT_DEVICE_NAME = "PREF_BT_DEVICE_NAME";
    private static final String PREF_BT_DEVICE_ADDRESS = "PREF_BT_DEVICE_ADDRESS";
    private static final String PREF_LAST_KNOWN_LAT = "PREF_LAST_KNOWN_LAT";
    private static final String PREF_LAST_KNOWN_LON = "PREF_LAST_KNOWN_LON";
    private static final String PREF_BT_DEVICE_CONNECTED = "PREF_BT_DEVICE_CONNECTED";
    private static final String PREF_METRIC = "PREF_METRIC";
    private static final String PREF_RUNNING = "PREF_RUNNING";

    public SharedPreferences mSharedPrefs;
    public SharedPreferences.Editor mEditor;

    public SharedPrefs(Context context) {
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getString() {
        return "String in Shared Prefs";
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

    public Double[] getLastLatLon() {
        Double[] lastLatLon = new Double[2];
        lastLatLon[0] = Double.valueOf(mSharedPrefs.getString(PREF_LAST_KNOWN_LAT, null));
        lastLatLon[1] = Double.valueOf(mSharedPrefs.getString(PREF_LAST_KNOWN_LON, null));
        return lastLatLon;
    }

    public void setLastLatLon(double lat, double lon) {
        mEditor = mSharedPrefs.edit();
        mEditor.putString(PREF_LAST_KNOWN_LAT, String.valueOf(lat));
        mEditor.putString(PREF_LAST_KNOWN_LON, String.valueOf(lon));
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

    public boolean getIsRunning() {
        return mSharedPrefs.getBoolean(PREF_RUNNING, false);
    }

    public void setIsRunning(boolean isRunning) {
        mEditor = mSharedPrefs.edit();
        mEditor.putBoolean(PREF_RUNNING, isRunning);
        mEditor.commit();
    }
}
