package com.bluetoothvehiclemonitor.btvm.viewmodels;

import android.app.Application;

import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;
import com.bluetoothvehiclemonitor.btvm.repository.MetricsRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class MetricsViewModel extends AndroidViewModel {

    private MetricsRepository mMetricsRepository;


    public MetricsViewModel(@NonNull Application application) {
        super(application);
        mMetricsRepository = MetricsRepository.getInstance(application.getApplicationContext());
    }

    public List<Metrics> getMetrics() {
        List<Metrics> metricsList = mMetricsRepository.getAllMetrics();
        return metricsList;
    }
}
