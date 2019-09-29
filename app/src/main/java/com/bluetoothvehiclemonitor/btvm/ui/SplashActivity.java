package com.bluetoothvehiclemonitor.btvm.ui;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.util.PermissionsUtil;
import com.bluetoothvehiclemonitor.btvm.viewmodels.SplashViewModel;
import com.bluetoothvehiclemonitor.btvm.viewmodels.ViewModelProviderFactory;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SplashActivity extends BaseActivity implements BottomSheetDialog.BottomSheetListener {
    private static final String TAG = "SplashActivity";

    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Inject ViewModelProviderFactory mProviderFactory;
    @Inject RequestManager mRequestManager;
    @Inject Drawable logo;
    @Inject DeviceAdapter mDeviceAdapter;
    @Inject BottomSheetDialog mDialog;

    ViewGroup mViewGroup;
    RecyclerView mRecyclerView;
    View mDeviceView;
    PopupWindow mDeviceWindow;
    SplashViewModel mSplashViewModel;
    ProgressBar mProgressBar;
    View mParent;

    TextView mAppInfo;
    TextView mTvNoShow;
    TextView mTvOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setLogo();
        mParent = findViewById(R.id.splash);
        mViewGroup = findViewById(android.R.id.content);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        mSplashViewModel = ViewModelProviders.of(this, mProviderFactory).get(SplashViewModel.class);
        checkPerms();
    }

    private void setLogo() {
        mRequestManager.load(logo).into((ImageView)findViewById(R.id.img_splash));
    }

    private void onPermissionSuccess(){
        getDeviceLocation();
        mSplashViewModel.setIsRunning(false);
        mProgressBar.setVisibility(View.GONE);
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    private void initAppInfo() {
        mAppInfo = findViewById(R.id.tv_app_description);
        mTvOk = findViewById(R.id.tv_ok);
        mTvNoShow = findViewById(R.id.tv_no_show);
        mProgressBar.setVisibility(View.GONE);
        mAppInfo.setVisibility(View.VISIBLE);
        mTvOk.setVisibility(View.VISIBLE);
        mTvNoShow.setVisibility(View.VISIBLE);
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPermissionSuccess();
            }
        });
        mTvNoShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSplashViewModel.setHasOnBoarded(true);
                onPermissionSuccess();
            }
        });

    }

    private void initDeviceWindow() {
        mDeviceView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.popup_device_picker, mViewGroup, false);
        mDeviceWindow = new PopupWindow(mDeviceView, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        mRecyclerView = mDeviceView.findViewById(R.id.device_list_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDeviceAdapter);
        mDeviceAdapter.setDeviceList(mSplashViewModel.getDevices());
        mDeviceAdapter.notifyDataSetChanged();
        mDeviceAdapter.setOnItemClickListener(new DeviceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                BluetoothDevice device = mSplashViewModel.mDevices.get(position);
                mSplashViewModel.setDevice(device.getName(), device.getAddress());
                BaseActivity.sBluetoothDevice = device;
                checkPerms();
                mDeviceWindow.dismiss();
            }
        });
        mDeviceWindow.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER, 0, 0);
    }

    public void checkPerms() {
        if(!mSplashViewModel.checkBluetoothRequirements(this) && mSplashViewModel.mDialogTv != null) {
            mDialog.show(getSupportFragmentManager(), "error");
        } else if (!mSplashViewModel.checkBluetoothRequirements(this)){
            if(mSplashViewModel.getAllPermissions(this)) {
                getDeviceLocation();
                if(mSplashViewModel.getSharedPrefs().mSharedPrefs.contains(SharedPrefs.PREF_ONBOARDED)) {
                    if(mSplashViewModel.getHasOnBoarded()) {
                        onPermissionSuccess();
                    } else {
                        initAppInfo();
                    }
                } else {
                    initAppInfo();
                }
            }
        } else {
            getDeviceLocation();
            if(mSplashViewModel.getSharedPrefs().mSharedPrefs.contains(SharedPrefs.PREF_ONBOARDED)) {
                if(mSplashViewModel.getHasOnBoarded()) {
                    onPermissionSuccess();
                } else {
                    initAppInfo();
                }
            } else {
                initAppInfo();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mSplashViewModel.getOnRequestPermissionsResult(requestCode, permissions, grantResults);
        if(PermissionsUtil.mLocationPermissionGranted){
            getDeviceLocation();
            if(mSplashViewModel.getSharedPrefs().mSharedPrefs.contains(SharedPrefs.PREF_ONBOARDED)) {
                if(mSplashViewModel.getHasOnBoarded()) {
                    onPermissionSuccess();
                } else {
                    initAppInfo();
                }
            } else {
                initAppInfo();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SplashViewModel.BLUETOOTH_ON_REQUEST_CODE) {
                checkPerms();
            }
        }
    }

    @Override
    public String updateDialogText() {
        return mSplashViewModel.mDialogTv;
    }

    @Override
    public String updateButtonText() {
        return mSplashViewModel.mBtnDialog;
    }

    @Override
    public void onButtonClicked() {
        if(!mSplashViewModel.onDialogClick(this)) {
            initDeviceWindow();
        }
    }

    public void storeLocation(Location location) {
        if(location == null) {
            if(!mSplashViewModel.getSharedPrefs().mSharedPrefs.contains(SharedPrefs.PREF_LAST_KNOWN_LAT)) {
                // Set location as Linbergh MARTA Station since we didn't get anything
                mSplashViewModel.setLastLatLon(33.823249, -84.369391);
                sCurrentLocation = new Location("location_default");
                sCurrentLocation.setLatitude(33.823249);
                sCurrentLocation.setLongitude(-84.369391);
            } else {
                // Get location from Shared Prefs
                sCurrentLocation = new Location("location_shared_prefs");
                Double[] d = mSplashViewModel.getLastLatLon();
                sCurrentLocation.setLatitude(d[0]);
                sCurrentLocation.setLongitude(d[1]);
            }
        } else {
            // Get actual location from GPS device
            sCurrentLocation = location;
            mSplashViewModel.setLastLatLon(location.getLatitude(), location.getLongitude());
        }
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()) { // We send currentLocation to broadcastLocation and then check for NULL
                        storeLocation((Location) task.getResult());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    storeLocation(null);
                }
            });
        } catch (SecurityException e) {

        }
    }
}
