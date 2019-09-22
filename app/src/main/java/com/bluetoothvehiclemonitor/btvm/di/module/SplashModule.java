package com.bluetoothvehiclemonitor.btvm.di.module;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import androidx.core.content.ContextCompat;
import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    static RequestOptions provideRequestOptions() {
        return RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);
    }

    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions) {
        return Glide.with(application).setDefaultRequestOptions(requestOptions);
    }

    @Provides
    static Drawable provideAppDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.drawable.ic_btvm);
    }

}
