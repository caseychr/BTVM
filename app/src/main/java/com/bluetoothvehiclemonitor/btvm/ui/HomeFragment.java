package com.bluetoothvehiclemonitor.btvm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bluetoothvehiclemonitor.btvm.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    /**
     * TODO
     * 1) Grab Metric and set units correctly, setup button, setup BT spinner
     * 2) Bluetooth Service -> set start/stop on button call -> set as background service
     * 3) Insert Metrics from BT on query end
     *
     * 4) Get current location
     * 5) Insert current location
     * 6) draw polylines and update on map
     * 7) Save trip (mapInfo & metrics) on STOP
     * 8) Animation from BT card to SettingsFragment
     * 9) Capture static map image and save
     *
     */

    View mView;
    Button mStartBtn;
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
    GoogleMap mGoogleMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        //if (PermissionsUtil.mLocationPermissionGranted) {
            /*if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }*/
            //mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            animateMap(new LatLng(33.900396, -84.277227), 16f);

       // }
    }

    private void initCard() {
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
    }

    /**
     * Create google map
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void animateMap(LatLng latLng, float zoom) {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
}
