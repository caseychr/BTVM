package com.bluetoothvehiclemonitor.btvm.ui;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.location.Location;
import android.provider.Settings;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    //@Inject
    //public SharedPrefs mSharedPrefs;

    public static BluetoothDevice sBluetoothDevice;
    public static Location sCurrentLocation;
    public static List<Location> mPolylines = new ArrayList<>();

    public static void setTitle(Activity activity, int title) {
        activity.setTitle(title);
    }
}
