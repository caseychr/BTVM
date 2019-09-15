package com.bluetoothvehiclemonitor.btvm.viewmodels;

import android.app.Application;

import com.bluetoothvehiclemonitor.btvm.data.local.room.BluetoothPIDDao;
import com.bluetoothvehiclemonitor.btvm.data.local.room.TripDao;
import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
import com.bluetoothvehiclemonitor.btvm.repository.TripRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HomeViewModel extends AndroidViewModel {

    Application mApplication;
    BluetoothPIDDao mBluetoothPIDDao;
    TripRepository mTripRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mTripRepository = TripRepository.getInstance(application);
    }

    public boolean isMetric() {
        return SharedPrefs.getInstance(mApplication.getApplicationContext()).getIsMetric();
    }

    public void insertBTPID(BluetoothPID bluetoothPID) {
        mBluetoothPIDDao.insert(bluetoothPID);
    }

    public LiveData<Trip> getTripById() {
        return mTripRepository.getTripById();
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


}
