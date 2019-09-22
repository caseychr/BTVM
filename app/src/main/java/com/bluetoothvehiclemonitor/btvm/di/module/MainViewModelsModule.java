package com.bluetoothvehiclemonitor.btvm.di.module;

import com.bluetoothvehiclemonitor.btvm.di.ViewModelKey;
import com.bluetoothvehiclemonitor.btvm.viewmodels.HomeViewModel;
import com.bluetoothvehiclemonitor.btvm.viewmodels.MainViewModel;
import com.bluetoothvehiclemonitor.btvm.viewmodels.MetricsViewModel;
import com.bluetoothvehiclemonitor.btvm.viewmodels.SettingsViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    public abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    public abstract ViewModel bindSettingsViewModel(SettingsViewModel settingsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MetricsViewModel.class)
    public abstract ViewModel bindMetricsViewModel(MetricsViewModel metricsViewModel);
}
