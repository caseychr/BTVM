package com.bluetoothvehiclemonitor.btvm.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.services.BluetoothService;
import com.bluetoothvehiclemonitor.btvm.services.GPSService;
import com.bluetoothvehiclemonitor.btvm.util.ConverterUtil;
import com.bluetoothvehiclemonitor.btvm.util.MapsUtil;
import com.bluetoothvehiclemonitor.btvm.viewmodels.HomeViewModel;
import com.driverapp.bluetoothandroidlibrary.MessageUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends Fragment implements OnMapReadyCallback, MessageUpdate {

    /**
     * TODO
     * 3) Get currentLocation on load
     * 2) Need to update list with current location
     * 1) Fix Bluetooth Library -> not polling and updating correctly
     *
     * 5) Insert current location
     * 6) draw polylines and update on map
     * 7) Save trip (mapInfo & metrics) on STOP
     * 8) Animation from BT card to SettingsFragment
     *
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
    List<LatLng> mLocationList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mGPSService = GPSService.newIntent(getActivity());
        mLocationList = new ArrayList<>();
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
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        MapsUtil.animateMap(mGoogleMap, new LatLng(33.900396, -84.277227), 16f);
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
                    mStartTv.setText("STOP");
                    mBluetoothService = BluetoothService.newIntent(getContext(),
                            BaseActivity.sBluetoothDevice, HomeFragment.this);
                    getActivity().startService(mGPSService);
                    getActivity().startService(mBluetoothService);
                } else if (mStartTv.getText().toString().equalsIgnoreCase("Stop")) {
                    mStartTv.setText("START");
                    MapsUtil.animateMapWithBounds(HomeFragment.this, mGoogleMap, mLocationList);
                    getActivity().stopService(mGPSService);
                    getActivity().stopService(mBluetoothService);
                }
            }
        });
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

    /**
     * Create google map
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void updateBTConnected(boolean b) {
        if(b) {
            mBTRunning.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mBTRunning.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            BluetoothPID bluetoothPID = new BluetoothPID(Float.valueOf(mDistanceNumTv.getText().toString()),
                    Float.valueOf(mSpeedNumTv.getText().toString()), Float.valueOf(mCoolantNumTv.getText().toString()),
                    Float.valueOf(mAirFlowNumTv.getText().toString()), Float.valueOf(mRPMNumTv.getText().toString()));
            mHomeViewModel.insertBTPID(bluetoothPID);
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
}
