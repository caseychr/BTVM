package com.bluetoothvehiclemonitor.btvm.di.module;

import com.bluetoothvehiclemonitor.btvm.di.ViewModelKey;
import com.bluetoothvehiclemonitor.btvm.viewmodels.SplashViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SplashViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    public abstract ViewModel bindSplashViewModel(SplashViewModel viewModel);
}
