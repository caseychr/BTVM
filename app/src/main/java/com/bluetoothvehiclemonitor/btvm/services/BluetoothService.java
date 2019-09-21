package com.bluetoothvehiclemonitor.btvm.services;

import android.app.IntentService;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import com.bluetoothvehiclemonitor.btvm.bluetooth.BluetoothController;
import com.bluetoothvehiclemonitor.btvm.bluetooth.MessageUpdate;


import androidx.annotation.Nullable;

public class BluetoothService extends IntentService {
    private static final String TAG = "BluetoothService";

    static Context mContext;
    static BluetoothController mBluetoothController;

    // Default Constructor for AndroidManifest
    public BluetoothService() {
        super(null);
    }

    public static Intent newIntent(Context context, BluetoothDevice device, MessageUpdate messageUpdate) {
        mContext = context;
        mBluetoothController = new BluetoothController("POLLING", device, messageUpdate);

        return new Intent(context, BluetoothService.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mBluetoothController.init(mContext);
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        mBluetoothController.init(mContext);
        return START_NOT_STICKY;
    }

}
