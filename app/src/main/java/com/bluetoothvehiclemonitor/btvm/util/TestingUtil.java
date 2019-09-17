package com.bluetoothvehiclemonitor.btvm.util;

import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;

import java.util.ArrayList;
import java.util.List;

public class TestingUtil {

    public static List<Metrics> getMockMetrics() {
        List<Metrics> metricsList = new ArrayList<>();
        Metrics metrics;
        for(int i=0;i<10;i++) {
            metrics = new Metrics(0, "10", "10", "10", "10", i+"");
            metricsList.add(metrics);
        }
        return metricsList;
    }

    public static BluetoothPID getMockPID() {
        BluetoothPID bluetoothPID;
        bluetoothPID = new BluetoothPID(0, 10, 10, 10, 10, 10);
        return bluetoothPID;
    }
}
