package com.bluetoothvehiclemonitor.btvm.data.local.room;

import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MetricsDao {

    @Insert
    void insert(Metrics metric);

    @Query("SELECT * FROM metrics_table ORDER BY mId")
    List<Metrics> getAllMetrics();

}
