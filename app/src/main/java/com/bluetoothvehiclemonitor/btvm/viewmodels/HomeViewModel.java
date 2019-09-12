package com.bluetoothvehiclemonitor.btvm.viewmodels;

import android.app.Application;

import com.bluetoothvehiclemonitor.btvm.data.local.room.BluetoothPIDDao;
import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends AndroidViewModel {

    Application mApplication;
    BluetoothPIDDao mBluetoothPIDDao;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
    }

    public boolean isMetric() {
        return SharedPrefs.getInstance(mApplication.getApplicationContext()).getIsMetric();
    }

    public void insertBTPID(BluetoothPID bluetoothPID) {
        mBluetoothPIDDao.insert(bluetoothPID);
    }
}
