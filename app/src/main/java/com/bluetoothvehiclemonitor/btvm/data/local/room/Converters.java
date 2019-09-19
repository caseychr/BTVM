package com.bluetoothvehiclemonitor.btvm.data.local.room;

import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class Converters {

    // Metrics converters
    @TypeConverter
    public static String fromMetrics(Metrics metrics) {
        if (metrics == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Metrics>() {

        }.getType();
        return gson.toJson(metrics, type);
    }

    @TypeConverter
    public static Metrics toMetrics(String metricString) {
        if (metricString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Metrics>() {
        }.getType();
        return gson.fromJson(metricString, type);
    }

    // LatLngs List converters
    @TypeConverter
    public static List<LatLng> toLatLngs(String latLngs) {
        Type listType = new TypeToken<List<LatLng>>() {}.getType();
        return new Gson().fromJson(latLngs, listType);
    }

    @TypeConverter
    public static String fromIntegerList(List<LatLng> latLngs) {
        Gson gson = new Gson();
        String json = gson.toJson(latLngs);
        return json;
    }

    // BT_PID List converters
    @TypeConverter
    public static List<BluetoothPID> toBT_PIDs(String BTPIDs) {
        Type listType = new TypeToken<List<BluetoothPID>>() {}.getType();
        return new Gson().fromJson(BTPIDs, listType);
    }

    @TypeConverter
    public static String fromBT_PIDsList(List<BluetoothPID> bluetoothPIDS) {
        Gson gson = new Gson();
        String json = gson.toJson(bluetoothPIDS);
        return json;
    }
}
