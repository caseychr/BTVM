package com.bluetoothvehiclemonitor.btvm.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
import com.bluetoothvehiclemonitor.btvm.services.BluetoothService;
import com.bluetoothvehiclemonitor.btvm.services.GPSService;
import com.bluetoothvehiclemonitor.btvm.util.ConverterUtil;
import com.bluetoothvehiclemonitor.btvm.util.DateUtil;
import com.bluetoothvehiclemonitor.btvm.util.MapsUtil;
import com.bluetoothvehiclemonitor.btvm.util.TestingUtil;
import com.bluetoothvehiclemonitor.btvm.viewmodels.HomeViewModel;
import com.driverapp.bluetoothandroidlibrary.MessageUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends Fragment implements OnMapReadyCallback, MessageUpdate {
    private static final String TAG = "HomeFragment";

    /**
     * TODO
     * 1) Fix Bluetooth Library -> not polling and updating correctly
     * 2) Refactor polylines update to be cleaner
     * 5) Animation from BT card to SettingsFragment
     */

    View mView;
    ImageView mStartBtn;
    TextView mBTRunning;
    TextView mStartTv;
    TextView mDistanceNumTv;
    TextView mSpeedNumTv;
    TextView mCoolantNumTv;
    TextView mAirFlowNumTv;
    TextView mRPMNumTv;
    TextView mDistanceTv;
    TextView mSpeedTv;
    TextView mCoolantTv;
    TextView mAirFlowTv;
    TextView mRPMTv;
    ProgressBar mProgressBar; //TODO need to show when BT is running
    GoogleMap mGoogleMap;

    private HomeViewModel mHomeViewModel;
    Intent mBluetoothService;
    Intent mGPSService;

    List<LatLng> mLocationList = new ArrayList<>();
    Trip mTrip;

    protected LocationReceiver mLocationReceiver;
    IntentFilter mIntentFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initBroadcastIntent();
        mLocationReceiver = new LocationReceiver();
        getActivity().registerReceiver(mLocationReceiver, mIntentFilter);
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mGPSService = GPSService.newIntent(getActivity());
        Log.i(TAG, "Device "+SharedPrefs.getInstance(getActivity()).getDevice()[0]+", "+
                SharedPrefs.getInstance(getActivity()).getDevice()[1]);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        BaseActivity.setTitle(getActivity(), R.string.home_title);
        initMap();
        initCard();
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mLocationReceiver);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        if(BaseActivity.sCurrentLocation != null) {
            Log.i(TAG, BaseActivity.sCurrentLocation.toString());
            MapsUtil.animateMap(mGoogleMap, new LatLng(BaseActivity.sCurrentLocation.getLatitude(),
                    BaseActivity.sCurrentLocation.getLongitude()), 16f);
        } else {
            Log.i(TAG, "No location retrieved");
            MapsUtil.animateMap(mGoogleMap, new LatLng(33.900396, -84.277227), 16f);
        }
    }

    /**
     * Create google map
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void initBroadcastIntent() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(GPSService.BROADCAST_ACTION);
        mIntentFilter.addCategory(GPSService.BROADCAST_CATEGORY);
    }

    private void initCard() {
        mStartBtn = mView.findViewById(R.id.btn_start);
        mStartTv = mView.findViewById(R.id.tv_start);
        mBTRunning = mView.findViewById(R.id.tv_bt_running);
        mProgressBar = mView.findViewById(R.id.pb_bt);
        mBTRunning.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);


        mDistanceNumTv = mView.findViewById(R.id.tv_service_miles_num);
        mSpeedNumTv = mView.findViewById(R.id.tv_speed_num);
        mCoolantNumTv = mView.findViewById(R.id.tv_coolant_num);
        mAirFlowNumTv = mView.findViewById(R.id.tv_airflow_num);
        mRPMNumTv = mView.findViewById(R.id.tv_engine_num);

        mDistanceTv = mView.findViewById(R.id.tv_service_miles);
        mSpeedTv = mView.findViewById(R.id.tv_speed);
        mCoolantTv = mView.findViewById(R.id.tv_coolant);
        mAirFlowTv = mView.findViewById(R.id.tv_airflow);
        mRPMTv = mView.findViewById(R.id.tv_engine);
        setUnits();

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mStartTv.getText().toString().equalsIgnoreCase("Start")) {
                    BaseActivity.mStartPressed = true;
                    startPressed();
                } else if (mStartTv.getText().toString().equalsIgnoreCase("Stop")) {
                    BaseActivity.mStartPressed = false;
                    stopPressed();
                }
            }
        });
    }

    private void startPressed() {
        mStartTv.setText("STOP");
        mGoogleMap.clear();

        mTrip = new Trip(DateUtil.getStringFromCurrentDate());
        mHomeViewModel.insertTrip(mTrip);
        List<Trip> trips = TestingUtil.getMockTrips();
        ////////// TESTING TRIPS
        /*for(Trip t:trips) {
            mHomeViewModel.insertTrip(t);
        }*/
        Log.i(TAG, BaseActivity.sBluetoothDevice.getName());
        mTrip.setTimeStamp(DateUtil.getStringFromCurrentDate());

        updatePolylineList(BaseActivity.sCurrentLocation);
        mBluetoothService = BluetoothService.newIntent(getContext(),
                BaseActivity.sBluetoothDevice, HomeFragment.this);
        Log.i(TAG, mTrip.getTimeStamp());
        subscribeObservers();
        getActivity().startService(mGPSService);
        getActivity().startService(mBluetoothService);
    }

    private void stopPressed() {
        mStartTv.setText("START");
        MapsUtil.animateMapWithBounds(HomeFragment.this, mGoogleMap, mLocationList);
        Log.i(TAG, "Stopping Services");
        GPSService.stopLocationPolling();
        getActivity().stopService(mGPSService);
        getActivity().stopService(mBluetoothService);
    }

    private void setUnits() {
        if(mHomeViewModel.isMetric()) {
            mDistanceTv.setText(R.string.metric_distance);
            mSpeedTv.setText(R.string.metric_speed);
            mCoolantTv.setText(R.string.metric_coolant);
            mAirFlowTv.setText(R.string.metric_airflow);
        } else {
            mDistanceTv.setText(R.string.imperial_distance);
            mSpeedTv.setText(R.string.imperial_speed);
            mCoolantTv.setText(R.string.imperial_coolant);
            mAirFlowTv.setText(R.string.imperial_airflow);
        }
    }

    private void updatePolylineList(Location location) {
        mLocationList.add(new LatLng(location.getLatitude(), location.getLongitude()));
        MapsUtil.updatePolylines(mGoogleMap, mLocationList);
        //mHomeViewModel.updateTrip(mTrip);

    }
    @Override
    public void updateBTConnected(boolean b) {
        if(b) {
            mBTRunning.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mBTRunning.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            if(mDistanceNumTv != null && mDistanceNumTv != null && mDistanceNumTv != null && mDistanceNumTv != null &&
            mDistanceNumTv != null){
                mTrip.getMetrics().getBluetoothPIDS().add(new BluetoothPID(Float.valueOf(mDistanceNumTv.getText().toString()),
                        Float.valueOf(mSpeedNumTv.getText().toString()), Float.valueOf(mCoolantNumTv.getText().toString()),
                        Float.valueOf(mAirFlowNumTv.getText().toString()), Float.valueOf(mRPMNumTv.getText().toString())));
                mHomeViewModel.updateTrip(mTrip);
            }
        }
    }

    @Override
    public void updateEngineRPM(String s) {
        mRPMNumTv.setText(s);
    }

    @Override
    public void updateVehicleSpeed(String s) {
        if(mHomeViewModel.isMetric()) {
            mSpeedNumTv.setText(s);
        } else {
            Float f = ConverterUtil.convertKMtoMiles(Float.valueOf(s));
            mSpeedNumTv.setText(String.valueOf(f));
        }
    }

    @Override
    public void updateAirFlow(String s) {
        if(mHomeViewModel.isMetric()) {
            mAirFlowNumTv.setText(s);
        } else {
            Float f = ConverterUtil.convertGramsToOunces(Float.valueOf(s));
            mAirFlowNumTv.setText(String.valueOf(f));
        }
    }

    @Override
    public void updateIntakeAirTemp(String s) {
        // Note used
    }

    @Override
    public void updateCoolantTemp(String s) {
        if(mHomeViewModel.isMetric()) {
            mCoolantNumTv.setText(s);
        } else {
            Float f = ConverterUtil.convertCelsiusToFahrenheit(Float.valueOf(s));
            mCoolantNumTv.setText(String.valueOf(f));
        }
    }

    @Override
    public void updateDistance(String s) {
        if(mHomeViewModel.isMetric()) {
            mDistanceNumTv.setText(s);
        } else {
            Float f = ConverterUtil.convertKMtoMiles(Float.valueOf(s));
            mDistanceNumTv.setText(String.valueOf(f));
        }
    }

    @Override
    public void updateErrorMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void subscribeObservers() {
        mHomeViewModel.getLatestTrip().observe(this, new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                if(BaseActivity.mStartPressed) {
                    if(trip.getLatLngs() != null) {
                        mLocationList.clear();
                        mLocationList = trip.getLatLngs();
                        mTrip = trip;
                        Log.i(TAG, trip.toString());
                    }
                }
            }
        });
    }

    public class LocationReceiver extends BroadcastReceiver {
        private static final String TAG = "LocationReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "HOME FRAGMENT IN RECEIVER");
            if(intent.hasExtra(GPSService.BROADCAST_TYPE_KEY)) {
                if(intent.getStringExtra(GPSService.BROADCAST_TYPE_KEY).equals(GPSService.NEW_LOCATION_BROADCAST)) {
                    Log.i(TAG, "received new location");
                    Location location = intent.getParcelableExtra(GPSService.CURRENT_LOCATION_KEY);
                    BaseActivity.sCurrentLocation = location;
                    updatePolylineList(location);
                } else if(intent.getStringExtra(GPSService.BROADCAST_TYPE_KEY).equals(GPSService.NO_LOCATION_BROADCAST)) {
                    Log.i(TAG, "NO new location");
                    Toast.makeText(context, "We are not receiving new location", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
