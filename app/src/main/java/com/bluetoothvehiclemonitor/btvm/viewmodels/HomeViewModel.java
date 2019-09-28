package com.bluetoothvehiclemonitor.btvm.viewmodels;

import android.app.Application;

import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
import com.bluetoothvehiclemonitor.btvm.repository.TripRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HomeViewModel extends AndroidViewModel {

    Application mApplication;
    TripRepository mTripRepository;

    @Inject
    public HomeViewModel(@NonNull Application application, TripRepository tripRepository) {
        super(application);
        mApplication = application;
        mTripRepository = tripRepository;
    }

    public String getSharedPrefsString() {
        return mTripRepository.getSharedPrefsString();
    }

    public boolean isMetric() {
        return mTripRepository.getIsMetric();
    }

    public LiveData<Trip> getLatestTrip() {
        return mTripRepository.getLatestTrip();
    }

    public LiveData<List<Trip>> getAllTrips() {
        return mTripRepository.getAllTrips();
    }

    public void insertTrip(Trip trip) {
        mTripRepository.insertTrip(trip);
    }

    public void updateTrip(Trip trip) {
        mTripRepository.updateTrip(trip);
    }

    public void deleteTrip(Trip trip) {
        mTripRepository.deleteTrip(trip);
    }

    public Double[] getLastLatLon() {
        return mTripRepository.getLastLatLon();
    }

    public void setLastLatLon(double lat, double lon) {
        mTripRepository.setLastLatLon(lat, lon);
    }

    public boolean getIsRunning() {
        return mTripRepository.getIsRunning();
    }

    public void setIsRunning(boolean running) {
        mTripRepository.setIsRunning(running);
    }

    public SharedPrefs getSharedPrefs(){return mTripRepository.mSharedPrefs;}

    public BluetoothPID getRunningBT() {
        return mTripRepository.getRunningBT();
    }

    public void setRunningBT(BluetoothPID bluetoothPID) {
        mTripRepository.setRunningBT(bluetoothPID);
    }
}
