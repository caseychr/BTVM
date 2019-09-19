package com.bluetoothvehiclemonitor.btvm.viewmodels;

import android.app.Application;

import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
import com.bluetoothvehiclemonitor.btvm.repository.TripRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MetricsViewModel extends AndroidViewModel {

    TripRepository mTripRepository;
    Application mApplication;

    public MetricsViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mTripRepository = TripRepository.getInstance(application);
    }

    public LiveData<List<Trip>> getAllTrips() {
        return mTripRepository.getAllTrips();
    }

    public boolean isMetric() {
        return SharedPrefs.getInstance(mApplication.getApplicationContext()).getIsMetric();
    }
}
