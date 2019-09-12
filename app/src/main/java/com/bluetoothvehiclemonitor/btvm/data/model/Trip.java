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
    private int mZoomLevel;
    private List<Float> mLats;
    private List<Float> mLons;
    private Metrics mMetrics;
    private String mTimeStamp;

    public Trip(int zoomLevel, List<Float> lats, List<Float> lons,
            Metrics metrics, String timeStamp) {
        mZoomLevel = zoomLevel;
        mLats = lats;
        mLons = lons;
        mMetrics = metrics;
        mTimeStamp = timeStamp;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getZoomLevel() {
        return mZoomLevel;
    }

    public void setZoomLevel(int zoomLevel) {
        mZoomLevel = zoomLevel;
    }

    public List<Float> getLats() {
        return mLats;
    }

    public void setLats(List<Float> lats) {
        mLats = lats;
    }

    public List<Float> getLons() {
        return mLons;
    }

    public void setLons(List<Float> lons) {
        mLons = lons;
    }

    public Metrics getMetrics() {
        return mMetrics;
    }

    public void setMetrics(Metrics metrics) {
        mMetrics = metrics;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        mTimeStamp = timeStamp;
    }
}
