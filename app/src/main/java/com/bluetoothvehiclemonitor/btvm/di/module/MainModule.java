package com.bluetoothvehiclemonitor.btvm.di.module;

import android.app.Application;

import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.di.scope.MainScope;
import com.bluetoothvehiclemonitor.btvm.repository.TripRepository;
import com.bluetoothvehiclemonitor.btvm.ui.MetricsAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    //TODO provide TripRepo instance
    /*static MetricsAdapter provideMetricsAdapter() {
        return new MetricsAdapter();
    };*/

    //TODO provide MetricsAdapter instance (Check if this is correct?)
    @MainScope
    @Provides
    static TripRepository provideTripRepository(SharedPrefs sharedPrefs, Application application) {
        return new TripRepository(sharedPrefs, application.getApplicationContext());
    };

    @MainScope
    @Provides
    static MetricsAdapter provideMetricsAdapter() {
        return new MetricsAdapter();
    }
}
