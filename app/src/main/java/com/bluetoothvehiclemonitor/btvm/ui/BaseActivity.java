package com.bluetoothvehiclemonitor.btvm.ui;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.location.Location;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public static BluetoothDevice sBluetoothDevice;
    public static Location sCurrentLocation;

    public static void setTitle(Activity activity, int title) {
        activity.setTitle(title);
    }
}
