package com.bluetoothvehiclemonitor.btvm.ui;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public static BluetoothDevice sBluetoothDevice;
    public static Location sCurrentLocation;
    public static boolean mStartPressed;
    public static List<Location> mPolylines = new ArrayList<>();

    public static void setTitle(Activity activity, int title) {
        activity.setTitle(title);
    }
}
