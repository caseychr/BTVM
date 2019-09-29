package com.bluetoothvehiclemonitor.btvm.util;

import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;

import java.text.DecimalFormat;
import java.util.List;

public class MetricsUtil {
    private static final String TAG = "MetricsUtil";

    public static final DecimalFormat df = new DecimalFormat("0.00");

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
            float distance = 0;
            for(int i=0;i<trips.size();i++) {

                airflow += Float.parseFloat(trips.get(i).getMetrics().getAirFlow());
                engineRPM += Float.parseFloat(trips.get(i).getMetrics().getEngineRPM());
                coolant += Float.parseFloat(trips.get(i).getMetrics().getCoolantTemp());
                speed += Float.parseFloat(trips.get(i).getMetrics().getVehicleSpeed());
                distance += Float.parseFloat(trips.get(i).getMetrics().getDistance());
            }
            /*if(trips.size() > 1) {
                distance = Float.parseFloat(trips.get(trips.size()-1).getMetrics().getDistance()) -
                        Float.parseFloat(trips.get(0).getMetrics().getDistance());
            } else {
                distance = Float.parseFloat(trips.get(0).getMetrics().getDistance());
            }*/
            airflow = airflow/trips.size();
            engineRPM = engineRPM/trips.size();
            coolant = coolant/trips.size();
            speed = speed/trips.size();
            metrics = new Metrics( String.valueOf(df.format(distance)), String.valueOf(df.format(airflow)),
                    String.valueOf(df.format(engineRPM)), String.valueOf(df.format(coolant)), String.valueOf(df.format(speed)));
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
            float distance = 0;
            for(int i=0;i<bluetoothPIDS.size();i++) {
                airflow += Math.round(bluetoothPIDS.get(i).getAirFlow());
                engineRPM += bluetoothPIDS.get(i).getEngineRPM();
                coolant += bluetoothPIDS.get(i).getCoolantTemp();
                speed += bluetoothPIDS.get(i).getVehicleSpeed();
            }
            if(bluetoothPIDS.size() > 1) {
                distance = bluetoothPIDS.get(bluetoothPIDS.size()-1).getDistance() - bluetoothPIDS.get(0).getDistance();
            }
            airflow = airflow/bluetoothPIDS.size();
            engineRPM = engineRPM/bluetoothPIDS.size();
            coolant = coolant/bluetoothPIDS.size();
            speed = speed/bluetoothPIDS.size();
            metrics = new Metrics( String.valueOf(df.format(distance)), String.valueOf(df.format(airflow)),
                    String.valueOf(df.format(engineRPM)), String.valueOf(df.format(coolant)), String.valueOf(df.format(speed)),
                    bluetoothPIDS);
            return metrics;
        }
    }
}
