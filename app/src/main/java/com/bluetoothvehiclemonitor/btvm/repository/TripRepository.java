package com.bluetoothvehiclemonitor.btvm.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.bluetoothvehiclemonitor.btvm.data.local.room.BTVMDatabase;
import com.bluetoothvehiclemonitor.btvm.data.local.room.TripDao;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TripRepository {

    private static TripRepository instance;

    private TripDao mTripDao;

    private TripRepository(Context context) {
        mTripDao = BTVMDatabase.getInstance(context).getTripDao();
    }

    public static TripRepository getInstance(Context context) {
        if(instance == null) {
            instance = new TripRepository(context);
        }
        return instance;
    }

    public void insertTrip(Trip trip) {
        new InsertTripAsyncTask(mTripDao).execute(trip);
    }

    public void updateTrip(Trip trip) {
        new UpdateTripAsyncTask(mTripDao).execute(trip);
    }

    public LiveData<Trip> getLatestTrip() {
        return mTripDao.getLatestTrip();
    }

    public LiveData<List<Trip>> getAllTrips() {
        return mTripDao.getAllTrips();
    }



    public static class InsertTripAsyncTask extends AsyncTask<Trip, Void, Void> {
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

    public static class UpdateTripAsyncTask extends AsyncTask<Trip, Void, Void> {
        private TripDao tripDao;

        private UpdateTripAsyncTask(TripDao tripDao) {
            this.tripDao = tripDao;
        }

        @Override
        protected Void doInBackground(Trip... trips) {
            tripDao.update(trips[0]);
            return null;
        }
    }
}
