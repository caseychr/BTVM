package com.bluetoothvehiclemonitor.btvm.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ConverterUtil {

    public static float convertKMtoMiles(float km) {
        float conversion = 0.621371F;
        float miles = km * conversion;
        return miles;
    }

    public static float convertCelsiusToFahrenheit(float degrees) {
        float fahrenheit = (float) ((degrees * 1.8) + 32);
        return fahrenheit;
    }

    public static float convertGramsToOunces(float grams) {
        float conversion = 0.035274F;
        float ounces = grams * conversion;
        return ounces;
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    public static Bitmap getBitmapFromBytes(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }
}
