package com.bluetoothvehiclemonitor.btvm.data.local.room;

import com.bluetoothvehiclemonitor.btvm.data.model.MapInfo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MapInfoDao {

    @Insert
    void insert(MapInfo mapInfo);

    @Update
    void update(MapInfo mapInfo);

    @Delete
    void delete(MapInfo mapInfo);

    @Query("DELETE FROM mapInfo_table")
    void  deleteAllMapInfoEntries();

    @Query("SELECT * FROM mapInfo_table ORDER BY mId DESC")
    LiveData<List<MapInfo>> getAllMapInfo();

    @Query("SELECT * FROM mapInfo_table WHERE mId = :id")
    LiveData<MapInfo> getMapInfoById(int id);

    @Query("SELECT * FROM mapInfo_table WHERE mDate = :date")
    LiveData<MapInfo> getMapInfoByDate(String date);

}
