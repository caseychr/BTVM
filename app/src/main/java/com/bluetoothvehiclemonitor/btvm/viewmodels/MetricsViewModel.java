package com.bluetoothvehiclemonitor.btvm.viewmodels;

import android.app.Application;

import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
import com.bluetoothvehiclemonitor.btvm.repository.TripRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MetricsViewModel extends AndroidViewModel {

    TripRepository mTripRepository;
    Application mApplication;

    @Inject
    public MetricsViewModel(@NonNull Application application, TripRepository tripRepository) {
        super(application);
        mApplication = application;
        mTripRepository = tripRepository;
    }

    public String getSharedPrefsString() {
        return mTripRepository.getSharedPrefsString();
    }

    public LiveData<List<Trip>> getAllTrips() {
        return mTripRepository.getAllTrips();
    }

    public boolean isMetric() {
        return mTripRepository.getIsMetric();
    }
}
