package com.bluetoothvehiclemonitor.btvm.data.local.room;

import android.content.Context;

import com.bluetoothvehiclemonitor.btvm.data.model.BluetoothPID;
import com.bluetoothvehiclemonitor.btvm.data.model.Metrics;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// No need for @TypeConverters
@Database(entities = {BluetoothPID.class, Metrics.class}, version = 1, exportSchema = false)
public abstract class BTVMDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "btvm_db";

    private static BTVMDatabase instance;

    public static BTVMDatabase getInstance(final Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BTVMDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }

    /**
     * Set these abstract methods to corresponding Dao instances in their respective repositories
     * @return
     */
    public abstract BluetoothPIDDao getBluetoothPIDDao();

    public abstract MetricsDao getMetricsDao();

}
