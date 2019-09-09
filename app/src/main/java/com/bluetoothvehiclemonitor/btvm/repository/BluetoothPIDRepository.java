package com.bluetoothvehiclemonitor.btvm.repository;

import android.content.Context;

import com.bluetoothvehiclemonitor.btvm.data.local.room.BTVMDatabase;
import com.bluetoothvehiclemonitor.btvm.data.local.room.BluetoothPIDDao;

public class BluetoothPIDRepository {

    private static BluetoothPIDRepository instance;

    private BluetoothPIDDao mBluetoothPIDDao;

    private BluetoothPIDRepository(Context context) {
        mBluetoothPIDDao = BTVMDatabase.getInstance(context).getBluetoothPIDDao();
    }

    public static BluetoothPIDRepository getInstance(Context context) {
        if(instance == null) {
            instance = new BluetoothPIDRepository(context);
        }
        return instance;
    }
}
