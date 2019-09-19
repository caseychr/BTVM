package com.bluetoothvehiclemonitor.btvm.data.model;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "metrics_table")
public class Metrics {

    @PrimaryKey
    @NonNull
    private int mId;
    private String mDistance;
    private String mAirFlow;
    private String mEngineRPM;
    private String mCoolantTemp;
    private String mVehicleSpeed;
    private List<BluetoothPID> mBluetoothPIDS;

    public Metrics() {
        mBluetoothPIDS = new ArrayList<>();
    }

    @Ignore
    public Metrics(String distance, String airFlow, String engineRPM, String coolantTemp, String vehicleSpeed,
            List<BluetoothPID> bluetoothPIDS) {
        mDistance = distance;
        mAirFlow = airFlow;
        mEngineRPM = engineRPM;
        mCoolantTemp = coolantTemp;
        mVehicleSpeed = vehicleSpeed;
        mBluetoothPIDS = bluetoothPIDS;
    }

    @Ignore
    public Metrics(String distance, String airFlow, String engineRPM, String coolantTemp, String vehicleSpeed) {
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

    public List<BluetoothPID> getBluetoothPIDS() {
        return mBluetoothPIDS;
    }

    public void setBluetoothPIDS(List<BluetoothPID> bluetoothPIDS) {
        mBluetoothPIDS = bluetoothPIDS;
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "mId=" + mId +
                ", mDistance='" + mDistance + '\'' +
                ", mAirFlow='" + mAirFlow + '\'' +
                ", mEngineRPM='" + mEngineRPM + '\'' +
                ", mCoolantTemp='" + mCoolantTemp + '\'' +
                ", mVehicleSpeed='" + mVehicleSpeed + '\'' +
                ", mBluetoothPIDS=" + mBluetoothPIDS +
                '}';
    }
}
