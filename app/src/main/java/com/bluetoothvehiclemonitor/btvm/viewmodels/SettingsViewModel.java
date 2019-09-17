package com.bluetoothvehiclemonitor.btvm.viewmodels;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends AndroidViewModel {
    private static final String TAG = "SettingsViewModel";

    Application mApplication;
    BluetoothAdapter mAdapter;
    List<BluetoothDevice> mDevices = new ArrayList<>();

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mAdapter != null) {
            mDevices.addAll(mAdapter.getBondedDevices());
        }
    }


    public List<BluetoothDevice> getDevices() {
        return mDevices;
    }

    public boolean isMetric() {
        return SharedPrefs.getInstance(mApplication.getApplicationContext()).getIsMetric();
    }

    public void setIsMetric(boolean isMetric) {
        SharedPrefs.getInstance(mApplication.getApplicationContext()).setIsMetric(isMetric);
    }

    public String[] getDevice() {
        return SharedPrefs.getInstance(mApplication.getApplicationContext()).getDevice();
    }

    public void setDevice(String device, String address) {
        SharedPrefs.getInstance(getApplication().getApplicationContext()).setDevice(device, address);
    }
}
