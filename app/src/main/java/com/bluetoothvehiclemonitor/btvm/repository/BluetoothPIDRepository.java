package com.bluetoothvehiclemonitor.btvm.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.bluetoothvehiclemonitor.btvm.data.local.room.BTVMDatabase;
import com.bluetoothvehiclemonitor.btvm.data.local.room.BluetoothPIDDao;
import com.bluetoothvehiclemonitor.btvm.data.local.room.MetricsDao;
import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BluetoothPIDRepository {

    private static BluetoothPIDRepository instance;

    private BluetoothPIDDao mBluetoothPIDDao;

    private BluetoothPIDRepository(Context context) {
        mBluetoothPIDDao = BTVMDatabase.getInstance(context).getBluetoothPIDDao();
    }

    public static BluetoothPIDRepository getInstance(Context context) {
        if(instance == null) {
            instance = new BluetoothPIDRepository(context);
        }
        return instance;
    }

    public LiveData<List<BluetoothPID>> getPIDsByTripId(int id) {
        return mBluetoothPIDDao.getPIDsByTripId(id);
    }

    public void insertPID(BluetoothPID bluetoothPID) {
        new InsertPIDAsyncTask(mBluetoothPIDDao).execute(bluetoothPID);
    }

    public static class InsertPIDAsyncTask extends AsyncTask<BluetoothPID, Void, Void> {
        private BluetoothPIDDao mBluetoothPIDDao;

        private InsertPIDAsyncTask(BluetoothPIDDao bluetoothPIDDao) {
            this.mBluetoothPIDDao = bluetoothPIDDao;
        }

        @Override
        protected Void doInBackground(BluetoothPID... pids) {
            mBluetoothPIDDao.insert(pids[0]);
            return null;
        }
    }
}
