package com.bluetoothvehiclemonitor.btvm.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bluetoothPID_table")
public class BluetoothPID {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;
    private float mDistance;
    private float mVehicleSpeed;
    private float mCoolantTemp;
    private float mAirFlow;
    private float mEngineRPM;

    public BluetoothPID(float distance, float vehicleSpeed, float coolantTemp, float airFlow,
            float engineRPM) {
        mDistance = distance;
        mVehicleSpeed = vehicleSpeed;
        mCoolantTemp = coolantTemp;
        mAirFlow = airFlow;
        mEngineRPM = engineRPM;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public float getDistance() {
        return mDistance;
    }

    public void setDistance(float distance) {
        mDistance = distance;
    }

    public float getVehicleSpeed() {
        return mVehicleSpeed;
    }

    public void setVehicleSpeed(float vehicleSpeed) {
        mVehicleSpeed = vehicleSpeed;
    }

    public float getCoolantTemp() {
        return mCoolantTemp;
    }

    public void setCoolantTemp(float coolantTemp) {
        mCoolantTemp = coolantTemp;
    }

    public float getAirFlow() {
        return mAirFlow;
    }

    public void setAirFlow(float airFlow) {
        mAirFlow = airFlow;
    }

    public float getEngineRPM() {
        return mEngineRPM;
    }

    public void setEngineRPM(float engineRPM) {
        mEngineRPM = engineRPM;
    }
}
