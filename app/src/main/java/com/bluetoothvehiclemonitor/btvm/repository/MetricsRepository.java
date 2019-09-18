package com.bluetoothvehiclemonitor.btvm.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.bluetoothvehiclemonitor.btvm.data.local.room.BTVMDatabase;
import com.bluetoothvehiclemonitor.btvm.data.local.room.MetricsDao;
import com.bluetoothvehiclemonitor.btvm.data.local.room.TripDao;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
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

    public void insertMetics(Metrics metrics) {
        new InsertMetricsAsyncTask(mMetricsDao).execute(metrics);
    }


    
    public List<Metrics> getAllMetrics() {
        return mMetricsDao.getAllMetrics();
    }

    public static class InsertMetricsAsyncTask extends AsyncTask<Metrics, Void, Void> {
        private MetricsDao metricsDao;

        private InsertMetricsAsyncTask(MetricsDao metricsDao) {
            this.metricsDao = metricsDao;
        }

        @Override
        protected Void doInBackground(Metrics... metrics) {
            metricsDao.insert(metrics[0]);
            return null;
        }
    }

}
