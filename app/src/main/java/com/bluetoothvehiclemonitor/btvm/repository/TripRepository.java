package com.bluetoothvehiclemonitor.btvm.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.bluetoothvehiclemonitor.btvm.data.local.room.BTVMDatabase;
import com.bluetoothvehiclemonitor.btvm.data.local.room.TripDao;
import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class TripRepository {

    public SharedPrefs mSharedPrefs;

    private TripDao mTripDao;

    @Inject
    public TripRepository(SharedPrefs sharedPrefs, Context context) {
        mSharedPrefs = sharedPrefs;
        mTripDao = BTVMDatabase.getInstance(context).getTripDao();
    }

    public String getSharedPrefsString() {
        return mSharedPrefs.getString();
    }

    public String[] getDevice() {
        return mSharedPrefs.getDevice();
    }

    public void setDevice(String device, String address) {
        mSharedPrefs.setDevice(device, address);
    }

    public Double[] getLastLatLon() {
        return mSharedPrefs.getLastLatLon();
    }

    public void setLastLatLon(double lat, double lon) {
        mSharedPrefs.setLastLatLon(lat, lon);
    }

    public boolean getIsMetric() {
        return mSharedPrefs.getIsMetric();
    }

    public void setIsMetric(boolean metric) {
        mSharedPrefs.setIsMetric(metric);
    }

    public boolean getIsRunning() {
        return mSharedPrefs.getIsRunning();
    }

    public void setIsRunning(boolean running) {
        mSharedPrefs.setIsRunning(running);
    }

    public BluetoothPID getRunningBT() {
        return mSharedPrefs.getLastPID();
    }

    public void setRunningBT(BluetoothPID bluetoothPID) {
        mSharedPrefs.setLastPID(bluetoothPID);
    }

    public void insertTrip(Trip trip) {
        new InsertTripAsyncTask(mTripDao).execute(trip);
    }

    public void updateTrip(Trip trip) {
        new UpdateTripAsyncTask(mTripDao).execute(trip);
    }

    public void deleteTrip(Trip trip) {
        new DeleteTripAsyncTask(mTripDao).execute(trip);
    }

    public LiveData<Trip> getLatestTrip() {
        return mTripDao.getLatestTrip();
    }

    public LiveData<List<Trip>> getAllTrips() {
        return mTripDao.getAllTrips();
    }

    public class InsertTripAsyncTask extends AsyncTask<Trip, Void, Void> {
        private TripDao tripDao;

        private InsertTripAsyncTask(TripDao tripDao) {
            this.tripDao = tripDao;
        }

        @Override
        protected Void doInBackground(Trip... trips) {
            tripDao.insert(trips[0]);
            return null;
        }
    }

    public class UpdateTripAsyncTask extends AsyncTask<Trip, Void, Void> {
        private TripDao tripDao;

        private UpdateTripAsyncTask(TripDao tripDao) {
            this.tripDao = tripDao;
        }

        @Override
        protected Void doInBackground(Trip... trips) {
            if(trips[0] != null) {
                tripDao.update(trips[0]);
            }
            return null;
        }
    }

    public class DeleteTripAsyncTask extends AsyncTask<Trip, Void, Void> {
        private TripDao tripDao;

        private DeleteTripAsyncTask(TripDao tripDao) {
            this.tripDao = tripDao;
        }

        @Override
        protected Void doInBackground(Trip... trips) {
            if(trips[0] != null) {
                tripDao.delete(trips[0]);
            }
            return null;
        }
    }
}
