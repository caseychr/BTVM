package com.bluetoothvehiclemonitor.btvm.util;


import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;

import java.util.List;

public class MetricsUtil {
    private static final String TAG = "MetricsUtil";

    public static Metrics getOverallMetrics(List<Trip> trips) {
        if(trips == null || (trips.get(0).getMetrics().getAirFlow() == null || trips.get(0).getMetrics().getCoolantTemp()  == null ||
        trips.get(0).getMetrics().getDistance()  == null || trips.get(0).getMetrics().getEngineRPM()  == null ||
                trips.get(0).getMetrics().getVehicleSpeed()  == null)) {
            return null;
        } else {
            Metrics metrics;
            float airflow = 0;
            float engineRPM = 0;
            float coolant = 0;
            float speed = 0;
            int distance = 0;
            for(int i=0;i<trips.size();i++) {

                airflow += Float.parseFloat(trips.get(i).getMetrics().getAirFlow());
                engineRPM += Float.parseFloat(trips.get(i).getMetrics().getEngineRPM());
                coolant += Float.parseFloat(trips.get(i).getMetrics().getCoolantTemp());
                speed += Float.parseFloat(trips.get(i).getMetrics().getVehicleSpeed());
                distance += Float.parseFloat(trips.get(i).getMetrics().getDistance());
            }
            airflow = airflow/trips.size();
            engineRPM = engineRPM/trips.size();
            coolant = coolant/trips.size();
            speed = speed/trips.size();
            metrics = new Metrics( String.valueOf(distance), String.valueOf(airflow), String.valueOf(engineRPM),
                    String.valueOf(coolant), String.valueOf(speed));
            return metrics;
        }
    }

    public static Metrics getTripMetrics(List<BluetoothPID> bluetoothPIDS) {
        if(bluetoothPIDS == null) {
            return null;
        } else {
            Metrics metrics;
            float airflow = 0;
            float engineRPM = 0;
            float coolant = 0;
            float speed = 0;
            int distance = 0;
            for(int i=0;i<bluetoothPIDS.size();i++) {
                airflow += bluetoothPIDS.get(i).getAirFlow();
                engineRPM += bluetoothPIDS.get(i).getEngineRPM();
                coolant += bluetoothPIDS.get(i).getCoolantTemp();
                speed += bluetoothPIDS.get(i).getVehicleSpeed();
                distance += bluetoothPIDS.get(i).getDistance();
            }
            airflow = airflow/bluetoothPIDS.size();
            engineRPM = engineRPM/bluetoothPIDS.size();
            coolant = coolant/bluetoothPIDS.size();
            speed = speed/bluetoothPIDS.size();
            metrics = new Metrics(String.valueOf(distance), String.valueOf(airflow), String.valueOf(engineRPM),
                    String.valueOf(coolant), String.valueOf(speed), bluetoothPIDS);
            return metrics;
        }
    }
}
