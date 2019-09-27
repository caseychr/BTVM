package com.bluetoothvehiclemonitor.btvm.viewmodels;

import android.app.Application;
import android.util.Log;

import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.repository.TripRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";

    public TripRepository mTripRepository;

    Application mApplication;

    @Inject
    public MainViewModel(@NonNull Application application, TripRepository tripRepository) {
        super(application);
        mTripRepository = tripRepository;
        mApplication = application;
    }

    public String getSharedPrefsString() {
        return mTripRepository.getSharedPrefsString();
    }

    public void setLastLatLon(Double lat, Double lon) {
        mTripRepository.setLastLatLon(lat, lon);
    }

    public void updateLatLonList() {
        //TODO use TripDao tp update list
    }

}
