package com.bluetoothvehiclemonitor.btvm.viewmodels;

import android.app.Application;

import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class MainViewModel extends AndroidViewModel {

    Application mApplication;
    //TripDap mDao

    public MainViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
    }

    public void setLastLatLon(Double lat, Double lon) {
        SharedPrefs.getInstance(mApplication.getApplicationContext()).setLastLatLon(lat, lon);
    }

    public void updateLatLonList() {
        //TODO use TripDao tp update list
    }

}
