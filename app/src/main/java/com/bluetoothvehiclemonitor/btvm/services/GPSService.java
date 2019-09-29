package com.bluetoothvehiclemonitor.btvm.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GPSService extends IntentService {
    private static final String TAG = "GPSService";

    private static final long DEFAULT_POLL_DELAY = 5 * 1000L; /* 5 seconds */

    public static final String BROADCAST_TYPE_KEY = "com.bluetoothvehiclemonitor.btvm.BROADCAST_TYPE_KEY";
    public static final String BROADCAST_ACTION = "com.bluetoothvehiclemonitor.btvm.BROADCAST_ACTION";
    public static final String BROADCAST_CATEGORY = "com.bluetoothvehiclemonitor.btvm.BROADCAST_CATEGORY";

    public static final String CURRENT_LOCATION_KEY = "com.bluetoothvehiclemonitor.btvm.CURRENT_LOCATION_KEY";
    public static final String NEW_LOCATION_BROADCAST = "NEW_LOCATION_BROADCAST";
    public static final String NO_LOCATION_BROADCAST = "NO_LOCATION_BROADCAST";

    private static Context mContext;
    private static Activity mActivity;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mCurrentLocation;
    private static Handler mHandler;
    private static boolean mIsPolling;
    private Runnable mCurrentLocationRunnable = new Runnable() {
        @Override
        public void run() {
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mActivity);
            try{
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()) {
                            //found location!
                            Location currentLocation = (Location) task.getResult();
                            Log.i(TAG, currentLocation.toString());
                            broadcastCurrentLocation(currentLocation);
                        } else {
                            //unable to get location
                        }
                    }
                });
            } catch (SecurityException e) {

            }
        }
    };

    public GPSService() {
        super(null);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
        mHandler = new Handler();
    }

    public static Intent newIntent(Context context, Activity activity) {
        mContext = context;
        mActivity = activity;
        return new Intent(context, GPSService.class);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        mIsPolling = true;
        mHandler.post(mCurrentLocationRunnable);
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    public static void stopLocationPolling() {
        Log.i(TAG, "stopping Location Polling");
        mIsPolling = false;
        mHandler.removeCallbacksAndMessages(null);
    }

    private void broadcastCurrentLocation(Location location) {
        Log.i(TAG, "broadcastCurrentLocation ");
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCAST_ACTION);
        broadcastIntent.addCategory(BROADCAST_CATEGORY);
        if (location != null) {
            Log.i(TAG, "location not null");
            broadcastIntent.putExtra(BROADCAST_TYPE_KEY, NEW_LOCATION_BROADCAST);
            broadcastIntent.putExtra(CURRENT_LOCATION_KEY, location);
        } else {
            broadcastIntent.putExtra(BROADCAST_TYPE_KEY, NO_LOCATION_BROADCAST);
        }
        sendBroadcast(broadcastIntent);
        Log.i(TAG, "sent broadcast "+location.toString());
        mHandler.postDelayed(mCurrentLocationRunnable, DEFAULT_POLL_DELAY);
    }
}
