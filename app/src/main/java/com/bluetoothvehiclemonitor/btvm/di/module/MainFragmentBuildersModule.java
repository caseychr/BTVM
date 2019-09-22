package com.bluetoothvehiclemonitor.btvm.di.module;

import com.bluetoothvehiclemonitor.btvm.ui.HomeFragment;
import com.bluetoothvehiclemonitor.btvm.ui.MetricsFragment;
import com.bluetoothvehiclemonitor.btvm.ui.SettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract HomeFragment contrbuteHomeFragment();

    @ContributesAndroidInjector
    abstract SettingsFragment contributeSettingsFragment();

    @ContributesAndroidInjector
    abstract MetricsFragment contributeMetricsFragment();
}
