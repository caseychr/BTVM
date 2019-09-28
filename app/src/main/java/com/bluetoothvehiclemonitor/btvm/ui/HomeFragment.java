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
import com.bluetoothvehiclemonitor.btvm.bluetooth.MessageUpdate;
import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Trip;
import com.bluetoothvehiclemonitor.btvm.services.BluetoothService;
import com.bluetoothvehiclemonitor.btvm.services.GPSService;
import com.bluetoothvehiclemonitor.btvm.util.ConverterUtil;
import com.bluetoothvehiclemonitor.btvm.util.DateUtil;
import com.bluetoothvehiclemonitor.btvm.util.MapsUtil;
import com.bluetoothvehiclemonitor.btvm.util.MetricsUtil;
import com.bluetoothvehiclemonitor.btvm.viewmodels.HomeViewModel;
import com.bluetoothvehiclemonitor.btvm.viewmodels.ViewModelProviderFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

public class HomeFragment extends DaggerFragment implements OnMapReadyCallback, MessageUpdate {
    private static final String TAG = "HomeFragment";

    @Inject ViewModelProviderFactory mProviderFactory;
    @Inject BottomSheetDialog mDialog;

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
    ProgressBar mProgressBar; //TODO need to show when BT is running //Databind
    GoogleMap mGoogleMap;

    private HomeViewModel mHomeViewModel;
    private boolean mIsRunning = false;
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
        mHomeViewModel = ViewModelProviders.of(this, mProviderFactory).get(HomeViewModel.class);
        mGPSService = GPSService.newIntent(getActivity());
        mIsRunning = mHomeViewModel.getIsRunning();
        Log.i(TAG, mHomeViewModel.getSharedPrefsString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        BaseActivity.setTitle(getActivity(), R.string.home_title);
        initMap();
        Log.i(TAG, "onCreateView");
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
            MapsUtil.animateMap(mGoogleMap, new LatLng(33.900396, -84.277227), 16f);
        }
    }

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
        if(mIsRunning) {
            if(mHomeViewModel.getSharedPrefs().mSharedPrefs.contains(SharedPrefs.PREF_PID_DISTANCE)) {
                BluetoothPID b = mHomeViewModel.getRunningBT();
                Log.i(TAG+" store_PIDS", mHomeViewModel.getRunningBT().toString());
                mDistanceNumTv.setText(String.valueOf(b.getDistance()));
                mSpeedNumTv.setText(String.valueOf(b.getVehicleSpeed()));
                mCoolantNumTv.setText(String.valueOf(b.getCoolantTemp()));
                mAirFlowNumTv.setText(String.valueOf(b.getAirFlow()));
                mRPMNumTv.setText(String.valueOf(b.getEngineRPM()));
            }
        }

        mDistanceTv = mView.findViewById(R.id.tv_service_miles);
        mSpeedTv = mView.findViewById(R.id.tv_speed);
        mCoolantTv = mView.findViewById(R.id.tv_coolant);
        mAirFlowTv = mView.findViewById(R.id.tv_airflow);
        mRPMTv = mView.findViewById(R.id.tv_engine);
        setUnits();
        if(mIsRunning) {
            mStartTv.setText(getString(R.string.btn_stop));
        } else {
            mStartTv.setText(getString(R.string.btn_start));
        }

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mIsRunning) {
                    mHomeViewModel.setIsRunning(true);
                    mIsRunning = true;
                    startPressed();
                } else {
                    mHomeViewModel.setIsRunning(false);
                    mIsRunning = false;
                    stopPressed();
                }
            }
        });
    }

    private void startPressed() {
        mStartTv.setText(getString(R.string.btn_stop));
        mGoogleMap.clear();

        mTrip = new Trip(DateUtil.getStringFromCurrentDate());
        mHomeViewModel.insertTrip(mTrip);

        mTrip.setTimeStamp(DateUtil.getStringFromCurrentDate());
        updatePolylineList(BaseActivity.sCurrentLocation);
        mBluetoothService = BluetoothService.newIntent(getContext(),
                BaseActivity.sBluetoothDevice, HomeFragment.this);
        subscribeObservers();
        getActivity().startService(mGPSService);
        getActivity().startService(mBluetoothService);
    }

    private void stopPressed() {
        mStartTv.setText(getString(R.string.btn_start));
        MapsUtil.animateMapWithBounds(HomeFragment.this, mGoogleMap, mLocationList);
        GPSService.stopLocationPolling();
        BluetoothService.setStillRunning(false);
        mBTRunning.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mTrip.setMetrics(MetricsUtil.getTripMetrics(mTrip.getMetrics().getBluetoothPIDS()));
        mHomeViewModel.updateTrip(mTrip);
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
        mHomeViewModel.updateTrip(mTrip);

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
                if(mIsRunning) {
                    Log.i(TAG+" store_PIDS", "setting PIDs");
                    //mHomeViewModel.setRunningBT(new BluetoothPID(4, 4, 4, 4, 4));
                    mHomeViewModel.setRunningBT(mTrip.getMetrics().getBluetoothPIDS().get(mTrip.getMetrics().getBluetoothPIDS().size()-1));
                }
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
        mStartTv.setText(getString(R.string.btn_start));
        GPSService.stopLocationPolling();
        mBTRunning.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        getActivity().stopService(mGPSService);
        getActivity().stopService(mBluetoothService);
        mIsRunning = false;
        mHomeViewModel.setIsRunning(false);
        mHomeViewModel.deleteTrip(mTrip);
        mDialog.show(getActivity().getSupportFragmentManager(), "bt");

    }

    private void subscribeObservers() {
        mHomeViewModel.getLatestTrip().observe(this, new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                if(mHomeViewModel.getIsRunning()) {
                    if(trip.getLatLngs() != null) {
                        mLocationList.clear();
                        mLocationList = trip.getLatLngs();
                    }
                }
                mTrip = trip;
            }
        });
    }

    public class LocationReceiver extends BroadcastReceiver {
        private static final String TAG = "LocationReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.hasExtra(GPSService.BROADCAST_TYPE_KEY)) {
                if(intent.getStringExtra(GPSService.BROADCAST_TYPE_KEY).equals(GPSService.NEW_LOCATION_BROADCAST)) {
                    Location location = intent.getParcelableExtra(GPSService.CURRENT_LOCATION_KEY);
                    BaseActivity.sCurrentLocation = location;
                    mHomeViewModel.setLastLatLon(location.getLatitude(), location.getLongitude());
                    updatePolylineList(location);
                } else if(intent.getStringExtra(GPSService.BROADCAST_TYPE_KEY).equals(GPSService.NO_LOCATION_BROADCAST)) {
                    Toast.makeText(context, "We are not receiving new location", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
