package com.bluetoothvehiclemonitor.btvm.di;

import com.bluetoothvehiclemonitor.btvm.di.module.MainFragmentBuildersModule;
import com.bluetoothvehiclemonitor.btvm.di.module.MainModule;
import com.bluetoothvehiclemonitor.btvm.di.module.MainViewModelsModule;
import com.bluetoothvehiclemonitor.btvm.di.module.SplashModule;
import com.bluetoothvehiclemonitor.btvm.di.module.SplashViewModelsModule;
import com.bluetoothvehiclemonitor.btvm.di.scope.MainScope;
import com.bluetoothvehiclemonitor.btvm.di.scope.SplashScope;
import com.bluetoothvehiclemonitor.btvm.ui.MainActivity;
import com.bluetoothvehiclemonitor.btvm.ui.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @SplashScope
    @ContributesAndroidInjector(modules = {SplashViewModelsModule.class, SplashModule.class})
    abstract SplashActivity contributeSplashActivity();

    @MainScope
    @ContributesAndroidInjector(modules = {MainFragmentBuildersModule.class,
            MainViewModelsModule.class, MainModule.class})
    abstract MainActivity contributeMainActivituy();
}
