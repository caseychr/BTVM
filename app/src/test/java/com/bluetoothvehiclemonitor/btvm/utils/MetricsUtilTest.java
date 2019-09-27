package com.bluetoothvehiclemonitor.btvm.utils;

import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
import com.bluetoothvehiclemonitor.btvm.util.MetricsUtil;
import com.bluetoothvehiclemonitor.btvm.util.TestingUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MetricsUtilTest {

    List<BluetoothPID> mBluetoothPIDS = new ArrayList<>();
    List<Trip> mTripList = new ArrayList<>();

    Metrics mMetrics_OVERALL_RESULT;
    Metrics mMetrics_RESULT;

    @BeforeEach
    public void setup() {
        mTripList = TestingUtil.getMockTrips();
        mBluetoothPIDS = mTripList.get(0).getMetrics().getBluetoothPIDS();

        mMetrics_RESULT = new Metrics("100", "10.0", "10.0", "10.0", "10.0",
                mTripList.get(0).getMetrics().getBluetoothPIDS());
        mMetrics_OVERALL_RESULT = new Metrics("1000", "10.0", "10.0", "10.0", "10.0");
    }

    @Test
    public void getOverallMetrics_returnMetrics() {
        Metrics m = MetricsUtil.getOverallMetrics(mTripList);
        Assertions.assertNotNull(m);
        Assertions.assertEquals(mMetrics_OVERALL_RESULT, m);
        System.out.println("getOverallMetrics_returnMetrics: SUCCESS");
    }

    @Test
    public void getTripMetrics_returnTripMetrics() {
        Metrics m = MetricsUtil.getTripMetrics(mBluetoothPIDS);
        Assertions.assertNotNull(m);
        Assertions.assertEquals(mMetrics_RESULT, m);
        System.out.println("getTripMetrics_returnTripMetrics: SUCCESS");
    }
}
