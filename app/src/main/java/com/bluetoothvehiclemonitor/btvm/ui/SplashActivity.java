package com.bluetoothvehiclemonitor.btvm.ui;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.util.PermissionsUtil;
import com.bluetoothvehiclemonitor.btvm.viewmodels.SplashViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SplashActivity extends BaseActivity implements BottomSheetDialog.BottomSheetListener {
    private static final String TAG = "SplashActivity";

    private FusedLocationProviderClient mFusedLocationProviderClient;

    ViewGroup mViewGroup;
    RecyclerView mRecyclerView;
    DeviceAdapter mDeviceAdapter;
    View mDeviceView;
    PopupWindow mDeviceWindow;
    SplashViewModel mSplashViewModel;
    BottomSheetDialog mDialog;
    ImageView mSpashView;
    ProgressBar mProgressBar;
    View mParent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getDeviceLocation();
        mParent = findViewById(R.id.splash);
        mViewGroup = findViewById(android.R.id.content);
        mSpashView = findViewById(R.id.img_splash);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        mDialog = new BottomSheetDialog();

        mSplashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        // Check perms and capability
        checkPerms();
        // TODO need to handle going to BT settings and coming back to app. Also need to handle onPaused state when on SplashActivity
    }

    // Move to MainActivity
    private void onPermissionSuccess(){
        mProgressBar.setVisibility(View.GONE);
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    // Show popupwindow of paired devices when one is not chosen
    private void initDeviceWindow() {
        mDeviceView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.popup_device_picker, mViewGroup, false);
        mDeviceWindow = new PopupWindow(mDeviceView, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        mRecyclerView = mDeviceView.findViewById(R.id.device_list_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(mDeviceAdapter == null){
            mDeviceAdapter = new DeviceAdapter(mSplashViewModel.getDevices());
            mRecyclerView.setAdapter(mDeviceAdapter);
        }
        mDeviceAdapter.notifyDataSetChanged();
        mDeviceAdapter.setOnItemClickListener(new DeviceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                BluetoothDevice device = mSplashViewModel.mDevices.get(position);
                SharedPrefs.getInstance(getApplicationContext()).setDevice(device.getName(), device.getAddress());
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
                onPermissionSuccess();
            }
        } else {
            onPermissionSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mSplashViewModel.getOnRequestPermissionsResult(requestCode, permissions, grantResults);
        if(PermissionsUtil.mLocationPermissionGranted){
            onPermissionSuccess();
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

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()) { // We send currentLocation to broadcastLocation and then check for NULL
                        //found location!
                        Log.i(TAG, "Does this RUN???");
                        sCurrentLocation = (Location) task.getResult();
                        //mMainViewModel.setLastLatLon(String.valueOf(sCurrentLocation.getLatitude()),
                          //      String.valueOf(sCurrentLocation.getLongitude()));
                        Log.i(TAG+" INIT", sCurrentLocation.toString());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // something happened no location found
                }
            });
        } catch (SecurityException e) {

        }
    }
}
