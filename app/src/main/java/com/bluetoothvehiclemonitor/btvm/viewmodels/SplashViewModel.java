package com.bluetoothvehiclemonitor.btvm.viewmodels;

import static com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs.PREF_BT_DEVICE_NAME;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.provider.Settings;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.ui.BaseActivity;
import com.bluetoothvehiclemonitor.btvm.util.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class SplashViewModel extends AndroidViewModel {
    private static final String TAG = "SplashViewModel";
    public static final int BLUETOOTH_ON_REQUEST_CODE = 2;

    @Inject
    public SharedPrefs mSharedPrefs;

    public Application mApplication;
    public boolean haveLocationPermissions;
    public boolean isBTCapable;
    public boolean haveBTPermissions;
    public boolean isBTOn;
    public boolean hasBTPairedDevice;
    public boolean hasBTConnectedDevice;
    public String mDialogTv;
    public String mBtnDialog;
    public BluetoothAdapter mAdapter;
    String[] mBluetoothDeviceArray;
    public List<BluetoothDevice> mDevices = new ArrayList<>();

    @Inject
    public SplashViewModel(Application application, SharedPrefs sharedPrefs) {
        super(application);
        mSharedPrefs = sharedPrefs;
        haveBTPermissions = false;
        haveLocationPermissions = false;
        isBTCapable = false;
        isBTOn = false;
        hasBTPairedDevice = false;
        hasBTConnectedDevice = false;

        mApplication = application;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mAdapter != null) {
            mDevices.addAll(mAdapter.getBondedDevices());
        }
        if(checkStoredDevice()) {
            mBluetoothDeviceArray = mSharedPrefs.getDevice();
        }
    }

    public String getSharedPrefsString() {
        return mSharedPrefs.getString();
    }

    private boolean checkStoredDevice() {
        return mSharedPrefs.mSharedPrefs.contains(PREF_BT_DEVICE_NAME);
    }

    private void setBluetoothDevice() {
        if(mBluetoothDeviceArray == null) {
            mBluetoothDeviceArray = new String[2];
        }
        String name = mBluetoothDeviceArray[0];
        String address = mBluetoothDeviceArray[1];
        for(BluetoothDevice device:mDevices) {
            if(device.getName().equals(name) && device.getAddress().equals(address)) {
                BaseActivity.sBluetoothDevice = device;
            }
        }
    }

    public boolean checkBluetoothRequirements(Activity activity) {
        if(mAdapter == null) { // is BT Capable?
            mDialogTv = activity.getString(R.string.perms_error_not_bt_capable);
            mBtnDialog = activity.getString(R.string.perms_btn_close_app);
        } else {
            if(mAdapter.isEnabled()) { // is BT On?

                if(mDevices.size() > 0) { // are there devices paired?
                    if(checkStoredDevice()) {
                        setBluetoothDevice();
                        if(PermissionsUtil.mLocationPermissionGranted) {
                            return true;
                        }
                    } else if(mAdapter.getBondedDevices().size() == 1) {
                        BaseActivity.sBluetoothDevice = mDevices.get(0);
                        /*SharedPrefs.getInstance(activity).setDevice(BaseActivity.sBluetoothDevice.getName(),
                                BaseActivity.sBluetoothDevice.getAddress());*/
                        mSharedPrefs.setDevice(BaseActivity.sBluetoothDevice.getName(),
                                BaseActivity.sBluetoothDevice.getAddress());
                        if(PermissionsUtil.mLocationPermissionGranted) {
                            return true;
                        }
                    } else {
                        mDialogTv = activity.getString(R.string.perms_error_btn_no_connect_device);
                        mBtnDialog = activity.getString(R.string.perms_btn_bt_choose_device);
                    }
                } else {
                    mDialogTv = activity.getString(R.string.perms_error_bt_no_paired_devices);
                    mBtnDialog = activity.getString(R.string.perms_btn_bt_settings);
                }
            } else {
                mDialogTv = activity.getString(R.string.perms_error_bt_not_on);
                mBtnDialog = activity.getString(R.string.perms_btn_bt_on);
            }
        }
        return false;
    }

    public boolean onDialogClick(Activity activity) {
        Intent intent;
        if(mBtnDialog.equals(activity.getString(R.string.perms_btn_close_app))) {
            activity.finish();
        } else if(mBtnDialog.equals(activity.getString(R.string.perms_btn_bt_on))) {
            intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mDialogTv = null;
            activity.startActivityForResult(intent, BLUETOOTH_ON_REQUEST_CODE);
        } else if(mBtnDialog.equals(activity.getString(R.string.perms_btn_bt_settings))) {
            intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            mDialogTv = null;
            activity.startActivity(intent);
        } else if(mBtnDialog.equals(activity.getString(R.string.perms_btn_bt_choose_device))) {
            mDialogTv = null;
            return false;
        }
        return true;
    }

    public List<BluetoothDevice> getDevices() {
        return mDevices;
    }

    public boolean getAllPermissions(Activity activity) {
        if(PermissionsUtil.getLocationPerms(activity)) {
            return true;
        }
        return false;
    }

    public void getOnRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        PermissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
