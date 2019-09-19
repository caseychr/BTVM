package com.bluetoothvehiclemonitor.btvm.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/staticmap?")
            .addConverterFactory(
            GsonConverterFactory.create());

    private static Retrofit sRetrofit = retrofitBuilder.build();

    private static RestApi sRestApi = sRetrofit.create(RestApi.class);

    public static RestApi getRestApi() {
        return sRestApi;
    }
}
