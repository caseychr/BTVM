package com.bluetoothvehiclemonitor.btvm.viewmodels;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.repository.TripRepository;
import com.bluetoothvehiclemonitor.btvm.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class SettingsViewModel extends AndroidViewModel {
    private static final String TAG = "SettingsViewModel";

    Application mApplication;
    BluetoothAdapter mAdapter;
    public TripRepository mTripRepository;
    List<BluetoothDevice> mDevices = new ArrayList<>();

    @Inject
    public SettingsViewModel(@NonNull Application application, TripRepository tripRepository) {
        super(application);
        mApplication = application;
        mTripRepository = tripRepository;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mAdapter != null) {
            mDevices.addAll(mAdapter.getBondedDevices());
        }
    }

    public String getSharedPrefsString() {
        return mTripRepository.getSharedPrefsString();
    }

    public List<BluetoothDevice> getDevices() {
        return mDevices;
    }

    public boolean isMetric() {
        return mTripRepository.getIsMetric();
    }

    public void setIsMetric(boolean isMetric) {
        mTripRepository.setIsMetric(isMetric);
    }

    public String[] getDevice() {
        return mTripRepository.getDevice();
    }

    public void setDevice(String device, String address) {
        mTripRepository.setDevice(device, address);
    }

    public boolean getIsRunning() {
        return mTripRepository.getIsRunning();
    }

    public void setIsRunning(boolean running) {
        mTripRepository.setIsRunning(running);
    }
}
