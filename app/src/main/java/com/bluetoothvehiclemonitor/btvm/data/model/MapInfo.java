package com.bluetoothvehiclemonitor.btvm.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mapInfo_table")
public class MapInfo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;
    private String mDate;
    private Double mLat;
    private Double mLon;

    public MapInfo(String date, Double lat, Double lon) {
        mDate = date;
        mLat = lat;
        mLon = lon;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        mLat = lat;
    }

    public Double getLon() {
        return mLon;
    }

    public void setLon(Double lon) {
        mLon = lon;
    }
}
