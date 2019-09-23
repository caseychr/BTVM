package com.bluetoothvehiclemonitor.btvm.di.module;

import android.app.Application;
import android.content.Context;

import com.bluetoothvehiclemonitor.btvm.data.local.sharedprefs.SharedPrefs;
import com.bluetoothvehiclemonitor.btvm.ui.BottomSheetDialog;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Context getContext(Application application){
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    static SharedPrefs getSharedPrefs(Application application) {
        return new SharedPrefs(getContext(application));
    }

    @Singleton
    @Provides
    static BottomSheetDialog getBottomSheetDialog() {
        return new BottomSheetDialog();
    }

    @Singleton
    @Provides
    static String someString() {
        return "this is a test string.";
    }
}
