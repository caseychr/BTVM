package com.bluetoothvehiclemonitor.btvm.util;

import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;

import java.util.List;

public class MetricsUtil {

    public static Metrics getOverallMetrics(List<Metrics> metricsList) {
        if(metricsList == null) {
            return null;
        } else {
            Metrics metrics;
            float airflow = 0;
            float engineRPM = 0;
            float coolant = 0;
            float speed = 0;
            int distance = 0;
            for(int i=0;i<metricsList.size();i++) {
                airflow += Float.parseFloat(metricsList.get(i).getAirFlow());
                engineRPM += Float.parseFloat(metricsList.get(i).getEngineRPM());
                coolant += Float.parseFloat(metricsList.get(i).getCoolantTemp());
                speed += Float.parseFloat(metricsList.get(i).getVehicleSpeed());
                distance += Float.parseFloat(metricsList.get(i).getDistance());
            }
            airflow = airflow/metricsList.size();
            engineRPM = engineRPM/metricsList.size();
            coolant = coolant/metricsList.size();
            speed = speed/metricsList.size();
            metrics = new Metrics(String.valueOf(distance), String.valueOf(airflow), String.valueOf(engineRPM),
                    String.valueOf(coolant), String.valueOf(speed));
            return metrics;
        }
    }
}
