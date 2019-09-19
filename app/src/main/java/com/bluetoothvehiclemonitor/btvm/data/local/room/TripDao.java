package com.bluetoothvehiclemonitor.btvm.data.local.room;

import com.bluetoothvehiclemonitor.btvm.data.model.Trip;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TripDao {

    @Insert
    void insert(Trip trip);

    @Update
    void update(Trip trip);

    @Query("SELECT * FROM trip ORDER BY mId")
    LiveData<List<Trip>> getAllTrips();

    @Query("SELECT * FROM trip ORDER BY mId DESC LIMIT 1")
    LiveData<Trip> getLatestTrip();

    @Query("SELECT * FROM trip WHERE mId = :id")
    LiveData<Trip> getTripById(int id);
}
