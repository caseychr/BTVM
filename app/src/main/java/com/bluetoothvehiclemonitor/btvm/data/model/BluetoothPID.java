package com.bluetoothvehiclemonitor.btvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    public BluetoothPID() {
    }

    @Ignore
    public BluetoothPID(float distance, float vehicleSpeed, float coolantTemp, float airFlow,
            float engineRPM) {

        mDistance = distance;
        mVehicleSpeed = vehicleSpeed;
        mCoolantTemp = coolantTemp;
        mAirFlow = airFlow;
        mEngineRPM = engineRPM;
    }

    protected BluetoothPID(Parcel in) {
        mId = in.readInt();
        mDistance = in.readFloat();
        mVehicleSpeed = in.readFloat();
        mCoolantTemp = in.readFloat();
        mAirFlow = in.readFloat();
        mEngineRPM = in.readFloat();
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

    @Override
    public String toString() {
        return "BluetoothPID{" +
                "mId=" + mId +
                ", mDistance=" + mDistance +
                ", mVehicleSpeed=" + mVehicleSpeed +
                ", mCoolantTemp=" + mCoolantTemp +
                ", mAirFlow=" + mAirFlow +
                ", mEngineRPM=" + mEngineRPM +
                '}';
    }
}
