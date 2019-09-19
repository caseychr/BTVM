package com.bluetoothvehiclemonitor.btvm.data.model;

import com.google.android.gms.maps.model.LatLng;

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
    private String mTimeStamp;
    private float mZoomLevel;
    private List<LatLng> mLatLngs;
    private Metrics mMetrics;

    public Trip(String timeStamp) {
        mTimeStamp = timeStamp;
        mMetrics = new Metrics();

    }

    @Ignore
    public Trip(String timeStamp, float zoomLevel, List<LatLng> latLngs,
            Metrics metrics) {
        mTimeStamp = timeStamp;
        mZoomLevel = zoomLevel;
        mLatLngs = latLngs;
        mMetrics = metrics;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        mTimeStamp = timeStamp;
    }

    public float getZoomLevel() {
        return mZoomLevel;
    }

    public void setZoomLevel(float zoomLevel) {
        mZoomLevel = zoomLevel;
    }

    public List<LatLng> getLatLngs() {
        return mLatLngs;
    }

    public void setLatLngs(List<LatLng> latLngs) {
        mLatLngs = latLngs;
    }

    public Metrics getMetrics() {
        return mMetrics;
    }

    public void setMetrics(Metrics metrics) {
        mMetrics = metrics;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "mId=" + mId +
                ", mTimeStamp='" + mTimeStamp + '\'' +
                ", mZoomLevel=" + mZoomLevel +
                ", mLatLngs=" + mLatLngs +
                ", mMetrics=" + mMetrics +
                '}';
    }
}
