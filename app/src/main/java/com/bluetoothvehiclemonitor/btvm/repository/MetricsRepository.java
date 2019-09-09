package com.bluetoothvehiclemonitor.btvm.repository;

import android.content.Context;

import com.bluetoothvehiclemonitor.btvm.data.local.room.BTVMDatabase;
import com.bluetoothvehiclemonitor.btvm.data.local.room.MetricsDao;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;
import com.bluetoothvehiclemonitor.btvm.util.TestingUtil;

import java.util.List;

public class MetricsRepository {

    private static MetricsRepository instance;

    private MetricsDao mMetricsDao;

    private MetricsRepository(Context context) {
        mMetricsDao = BTVMDatabase.getInstance(context).getMetricsDao();
    }

    public static MetricsRepository getInstance(Context context) {
        if(instance == null) {
            instance = new MetricsRepository(context);

        }
        return instance;
    }

    public List<Metrics> getAllMetrics() {
        return mMetricsDao.getAllMetrics();
    }

}
