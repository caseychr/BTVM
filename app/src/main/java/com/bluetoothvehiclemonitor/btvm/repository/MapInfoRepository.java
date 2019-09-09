package com.bluetoothvehiclemonitor.btvm.repository;

import android.content.Context;

import com.bluetoothvehiclemonitor.btvm.data.local.room.BTVMDatabase;
import com.bluetoothvehiclemonitor.btvm.data.local.room.MapInfoDao;

public class MapInfoRepository {

    private static MapInfoRepository instance;

    private MapInfoDao mMapInfoDao;

    private MapInfoRepository(Context context) {
        mMapInfoDao = BTVMDatabase.getInstance(context).getMapInfoDao();
    }

    public static MapInfoRepository getInstance(Context context) {
        if(instance == null) {
            instance = new MapInfoRepository(context);
        }
        return instance;
    }

}
