package com.bluetoothvehiclemonitor.btvm.util;

import android.app.Activity;
import android.location.Location;
import android.text.format.DateUtils;
import android.view.View;

import com.bluetoothvehiclemonitor.btvm.R;
import com.bluetoothvehiclemonitor.btvm.ui.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import androidx.fragment.app.Fragment;

public class MapsUtil {

    private static final float DEFAULT_ZOOM = 16f;

    public static void animateMap(GoogleMap googleMap, LatLng latLng, float zoom) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public static void updatePolylines(GoogleMap googleMap, List<LatLng> locationList) {
        PolylineOptions mOptions = new PolylineOptions();
        googleMap.clear();
        mOptions.getPoints().clear();
        mOptions.color(R.color.color_brown_purple);
        mOptions.width(10);
        mOptions.addAll(locationList);
        googleMap.addPolyline(mOptions);
        googleMap.addMarker(new MarkerOptions().position(locationList.get(0))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        googleMap.addMarker(new MarkerOptions().position(locationList.get(locationList.size()-1))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        animateMap(googleMap, new LatLng(locationList.get(locationList.size()-1).latitude,
                locationList.get(locationList.size()-1).longitude), DEFAULT_ZOOM);
    }

    public static void animateMapWithBounds(Fragment fragment, GoogleMap googleMap, List<LatLng> locationList) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if(locationList != null && !locationList.isEmpty()) {
            builder.include(locationList.get(0));
            int padding = (int) (fragment.getView().getWidth() * 0.3);
            builder.include(locationList.get(locationList.size()-1));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), padding));
        }
    }
}
