package com.bluetoothvehiclemonitor.btvm.util;

import android.util.Log;

import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class TestingUtil {
    private static final String TAG = "TestingUtil";

    public static List<Trip> getMockTrips() {
        List<BluetoothPID> bluetoothPIDS = new ArrayList<>();
        BluetoothPID bluetoothPID;
        for(int i=0;i<10;i++) {
            bluetoothPID = new BluetoothPID(10,10,10,10,10);
            bluetoothPIDS.add(bluetoothPID);
        }

        List<Metrics> metricsList = new ArrayList<>();
        Metrics metrics;
        for(int i=0;i<10;i++) {
            metrics = MetricsUtil.getTripMetrics(bluetoothPIDS);
            metricsList.add(metrics);
        }

        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(33.900372, -84.277207));
        latLngs.add(new LatLng(33.908984, -84.266826));
        latLngs.add(new LatLng(33.911023, -84.267609));
        latLngs.add(new LatLng(33.911023, -84.267609));
        latLngs.add(new LatLng(33.916641, -84.259498));

        List<Trip> trips = new ArrayList<>();
        Trip trip;
        for(int i=0;i<10;i++) {
            trip = new Trip("09-18-2019 12:12:12", 16f, latLngs, metricsList.get(i));
            trips.add(trip);
        }
        return trips;
    }

    public static List<Trip> getOneNullTrip() {
        List<Metrics> metricsList = new ArrayList<>();
        Metrics metrics = new Metrics();
        metricsList.add(metrics);

        List<Trip> trips = new ArrayList<>();
        Trip trip = new Trip("09-18-2019 12:12:12", 0, null, metrics);
        trips.add(trip);
        return trips;
    }

    public static List<Trip> getListOfValidTrips() {
        List<BluetoothPID> bluetoothPIDS = new ArrayList<>();
        BluetoothPID bluetoothPID;
        for(int i=0;i<10;i++) {
            bluetoothPID = new BluetoothPID(10,10,10,10,10);
            bluetoothPIDS.add(bluetoothPID);
        }

        List<Metrics> metricsList = new ArrayList<>();
        Metrics metrics;
        for(int i=0;i<10;i++) {
            metrics = MetricsUtil.getTripMetrics(bluetoothPIDS);
            metricsList.add(metrics);
        }

        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(33.900372, -84.277207));
        latLngs.add(new LatLng(33.908984, -84.266826));
        latLngs.add(new LatLng(33.911023, -84.267609));
        latLngs.add(new LatLng(33.911023, -84.267609));
        latLngs.add(new LatLng(33.916641, -84.259498));

        List<Trip> trips = new ArrayList<>();
        Trip trip;
        for(int i=0;i<10;i++) {
            trip = new Trip("09-18-2019 12:12:12", 16f, latLngs, metricsList.get(i));
            trips.add(trip);
        }
        return trips;
    }

    public static List<Trip> getListWithLastTripInvalid() {
        List<BluetoothPID> bluetoothPIDS = new ArrayList<>();
        BluetoothPID bluetoothPID;
        for(int i=0;i<11;i++) {
            bluetoothPID = new BluetoothPID(10,10,10,10,10);
            bluetoothPIDS.add(bluetoothPID);
        }

        List<Metrics> metricsList = new ArrayList<>();
        Metrics metrics;
        for(int i=0;i<10;i++) {
            metrics = MetricsUtil.getTripMetrics(bluetoothPIDS);
            metricsList.add(metrics);
        }
        Metrics metrics1 = new Metrics();
        metricsList.add(metrics1);

        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(33.900372, -84.277207));
        latLngs.add(new LatLng(33.908984, -84.266826));
        latLngs.add(new LatLng(33.911023, -84.267609));
        latLngs.add(new LatLng(33.911023, -84.267609));
        latLngs.add(new LatLng(33.916641, -84.259498));

        List<Trip> trips = new ArrayList<>();
        Trip trip;
        for(int i=0;i<11;i++) {
            trip = new Trip("09-18-2019 12:12:12", 16f, latLngs, metricsList.get(i));
            trips.add(trip);
        }
        return trips;
    }

    public static List<Trip> getListWithInvalidTripsMixedIn() {
        List<BluetoothPID> bluetoothPIDS = new ArrayList<>();
        BluetoothPID bluetoothPID;
        for(int i=0;i<10;i++) {
            bluetoothPID = new BluetoothPID(10,10,10,10,10);
            bluetoothPIDS.add(bluetoothPID);
        }

        List<Metrics> metricsList = new ArrayList<>();
        Metrics metrics;
        for(int i=0;i<3;i++) {
            metrics = MetricsUtil.getTripMetrics(bluetoothPIDS);
            Log.i(TAG, metrics.toString());
            metricsList.add(metrics);
        }
        for(int i=0;i<2;i++) {
            metrics = new Metrics();
            Log.i(TAG, metrics.toString());
            metricsList.add(metrics);
        }
        for(int i=0;i<3;i++) {
            metrics = MetricsUtil.getTripMetrics(bluetoothPIDS);
            Log.i(TAG, metrics.toString());
            metricsList.add(metrics);
        }
        for(int i=0;i<2;i++) {
            metrics = new Metrics();
            Log.i(TAG, metrics.toString());
            metricsList.add(metrics);
        }

        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(33.900372, -84.277207));
        latLngs.add(new LatLng(33.908984, -84.266826));
        latLngs.add(new LatLng(33.911023, -84.267609));
        latLngs.add(new LatLng(33.911023, -84.267609));
        latLngs.add(new LatLng(33.916641, -84.259498));

        List<Trip> trips = new ArrayList<>();
        Trip trip;
        for(int i=0;i<10;i++) {
            trip = new Trip("09-18-2019 12:12:12", 16f, latLngs, metricsList.get(i));
            trips.add(trip);
        }
        Log.i(TAG, metricsList.toString());
        Log.i(TAG, trips.toString());
        return trips;
    }
}
