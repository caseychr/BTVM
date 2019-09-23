package com.bluetoothvehiclemonitor.btvm.di.module;

import android.app.Application;
import android.content.Context;

import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.ui.BottomSheetDialog;
import com.bluetoothvehiclemonitor.btvm.ui.DeviceAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Context provideContext(Application application){
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    static SharedPrefs provideSharedPrefs(Application application) {
        return new SharedPrefs(provideContext(application));
    }

    @Singleton
    @Provides
    static DeviceAdapter provideDeviceAdapter(Application application) {
        return new DeviceAdapter(provideSharedPrefs(application), provideContext(application));
    }

    @Singleton
    @Provides
    static BottomSheetDialog provideDialog() {
        return new BottomSheetDialog();
    }

    @Singleton
    @Provides
    static String someString() {
        return "this is a test string.";
    }
}
