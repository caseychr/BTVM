package com.bluetoothvehiclemonitor.btvm.data.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity()
public class Trip {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;
    private int mZoomLevel;
    private List<Double> mLats;
    private List<Double> mLons;
    private int mMetricsId;
    private String mTimeStamp;

    private List<Integer> mBluetooth_PID;

    public Trip() {
    }

    @Ignore
    public Trip(int zoomLevel, ArrayList<Double> lats, ArrayList<Double> lons, int metricsId, String timeStamp,
            ArrayList<Integer> bluetooth_PID) {
        mZoomLevel = zoomLevel;
        mLats = lats;
        mLons = lons;
        mMetricsId = metricsId;
        mTimeStamp = timeStamp;
        mBluetooth_PID = bluetooth_PID;
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

    public List<Double> getLats() {
        return mLats;
    }

    public void setLats(List<Double> lats) {
        mLats = lats;
    }

    public List<Double> getLons() {
        return mLons;
    }

    public void setLons(List<Double> lons) {
        mLons = lons;
    }

    public int getMetricsId() {
        return mMetricsId;
    }

    public void setMetricsId(int metricsId) {
        mMetricsId = metricsId;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        mTimeStamp = timeStamp;
    }

    public List<Integer> getBluetooth_PID() {
        return mBluetooth_PID;
    }

    public void setBluetooth_PID(List<Integer> bluetooth_PID) {
        mBluetooth_PID = bluetooth_PID;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "mId=" + mId +
                ", mZoomLevel=" + mZoomLevel +
                ", mLats=" + mLats +
                ", mLons=" + mLons +
                ", mMetricsId=" + mMetricsId +
                ", mTimeStamp='" + mTimeStamp + '\'' +
                ", mBluetooth_PID=" + mBluetooth_PID +
                '}';
    }
}
