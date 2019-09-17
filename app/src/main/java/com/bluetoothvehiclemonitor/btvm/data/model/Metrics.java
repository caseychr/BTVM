package com.bluetoothvehiclemonitor.btvm.data.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "metrics_table")
public class Metrics {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;
    private int mTripId;
    private String mDistance;
    private String mAirFlow;
    private String mEngineRPM;
    private String mCoolantTemp;
    private String mVehicleSpeed;

    public Metrics(int tripId, String distance, String airFlow, String engineRPM, String coolantTemp, String vehicleSpeed) {
        mTripId = tripId;
        mDistance = distance;
        mAirFlow = airFlow;
        mEngineRPM = engineRPM;
        mCoolantTemp = coolantTemp;
        mVehicleSpeed = vehicleSpeed;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getTripId() {
        return mTripId;
    }

    public void setTripId(int tripId) {
        mTripId = tripId;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getAirFlow() {
        return mAirFlow;
    }

    public void setAirFlow(String airFlow) {
        mAirFlow = airFlow;
    }

    public String getEngineRPM() {
        return mEngineRPM;
    }

    public void setEngineRPM(String engineRPM) {
        mEngineRPM = engineRPM;
    }

    public String getCoolantTemp() {
        return mCoolantTemp;
    }

    public void setCoolantTemp(String coolantTemp) {
        mCoolantTemp = coolantTemp;
    }

    public String getVehicleSpeed() {
        return mVehicleSpeed;
    }

    public void setVehicleSpeed(String vehicleSpeed) {
        mVehicleSpeed = vehicleSpeed;
    }

    /*public List<Integer> getBluetoothPIDS_Integers() {
        return mBluetoothPIDS_Integers;
    }

    public void setBluetoothPIDS_Integers(List<Integer> bluetoothPIDS_Integers) {
        mBluetoothPIDS_Integers = bluetoothPIDS_Integers;
    }*/
}
