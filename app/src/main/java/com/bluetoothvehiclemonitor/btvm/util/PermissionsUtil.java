package com.bluetoothvehiclemonitor.btvm.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsUtil {
    private static final String TAG = "PermissionsUtil";

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 0;

    public static boolean mLocationPermissionGranted = false;

    public static String[] LocationPerms = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public static boolean getLocationPerms(Activity activity) {
        Log.i(TAG, "getAllLocPerms");
        if(ContextCompat.checkSelfPermission(activity, LocationPerms[0]) == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(activity, LocationPerms[1]) == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Loc Perms TRUE");
                mLocationPermissionGranted = true;
            } else {
                Log.i(TAG, "Loc Perms FALSE");
                ActivityCompat.requestPermissions(activity, LocationPerms, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {Log.i(TAG, "Loc Perms FALSE2");

            ActivityCompat.requestPermissions(activity, LocationPerms, LOCATION_PERMISSION_REQUEST_CODE);
        }
        return (mLocationPermissionGranted);
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if(grantResults.length > 0) {
                    for(int i=0;i<grantResults.length;i++) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                }
            }
        }
    }
}
