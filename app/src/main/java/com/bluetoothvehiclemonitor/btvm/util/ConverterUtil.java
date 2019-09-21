package com.bluetoothvehiclemonitor.btvm.util;

import java.text.DecimalFormat;

public class ConverterUtil {

    private static DecimalFormat df = new DecimalFormat("0.00");

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

    public static String convertKMtoMiles(String km) {
        float fkm = Float.valueOf(km);
        float conversion = 0.621371F;
        float miles = fkm * conversion;
        return String.valueOf(df.format(miles));
    }

    public static String convertCelsiusToFahrenheit(String degrees) {
        float fdegrees = Float.valueOf(degrees);
        float fahrenheit = (float) ((fdegrees * 1.8) + 32);
        return String.valueOf(df.format(fahrenheit));
    }

    public static String convertGramsToOunces(String grams) {
        float fgms = Float.valueOf(grams);
        float conversion = 0.035274F;
        float ounces = fgms * conversion;
        return String.valueOf(df.format(ounces));
    }
}
