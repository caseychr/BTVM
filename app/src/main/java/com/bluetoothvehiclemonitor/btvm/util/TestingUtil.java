package com.bluetoothvehiclemonitor.btvm.util;

import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;

import java.util.ArrayList;
import java.util.List;

public class TestingUtil {

    public static List<Metrics> getMockMetrics() {
        List<Metrics> metricsList = new ArrayList<>();
        Metrics metrics;
        for(int i=0;i<10;i++) {
            metrics = new Metrics("10", "10", "10", "10", i+"");
            metricsList.add(metrics);
        }
        return metricsList;
    }
}
