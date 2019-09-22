package com.bluetoothvehiclemonitor.btvm.di;

import android.app.Application;

import com.bluetoothvehiclemonitor.btvm.BTVMApp;
import com.bluetoothvehiclemonitor.btvm.di.module.AppModule;
import com.bluetoothvehiclemonitor.btvm.di.module.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ActivityBuildersModule.class,
        AppModule.class, ViewModelFactoryModule.class})
public interface AppComponent extends AndroidInjector<BTVMApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
