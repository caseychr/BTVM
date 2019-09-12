package com.bluetoothvehiclemonitor.btvm.data.local.room;

import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BluetoothPIDDao {

    @Insert
    void insert(BluetoothPID pid);

    @Update
    void update(BluetoothPID pid);

    @Delete
    void delete(BluetoothPID pid);

    @Query("DELETE FROM bluetoothPID_table")
    void  deleteAllPIDEntries();

    @Query("SELECT * FROM bluetoothPID_table ORDER BY mId DESC")
    LiveData<List<BluetoothPID>> getAllPIDs();

    @Query("SELECT * FROM bluetoothPID_table WHERE mId = :id")
    LiveData<BluetoothPID> getPIDById(int id);
}
