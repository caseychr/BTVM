package com.bluetoothvehiclemonitor.btvm.data.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip_table")
public class Trip {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;
    private MapInfo mMapInfo;
    private Metrics mMetrics;
    private List<BluetoothPID> mPIDList;

    public Trip(MapInfo mapInfo, Metrics metrics,
            List<BluetoothPID> PIDList) {
        mMapInfo = mapInfo;
        mMetrics = metrics;
        mPIDList = PIDList;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public MapInfo getMapInfo() {
        return mMapInfo;
    }

    public void setMapInfo(MapInfo mapInfo) {
        mMapInfo = mapInfo;
    }

    public Metrics getMetrics() {
        return mMetrics;
    }

    public void setMetrics(Metrics metrics) {
        mMetrics = metrics;
    }

    public List<BluetoothPID> getPIDList() {
        return mPIDList;
    }

    public void setPIDList(List<BluetoothPID> PIDList) {
        mPIDList = PIDList;
    }
}
