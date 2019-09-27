package com.bluetoothvehiclemonitor.btvm.utils;

import com.bluetoothvehiclemonitor.btvm.util.ConverterUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConverterUtilTest {

    public static final String TEST_DATA_STRING = "15";
    public static final float TEST_DATA_FLOAT = 15;

    public static final String TEST_DATA_STRING_RESULT_MILES = "9.32";
    public static final float TEST_DATA_FLOAT_RESULT_MILES = 9.320564F;
    public static final String TEST_DATA_STRING_RESULT_DEGREES = "59.00";
    public static final float TEST_DATA_FLOAT_RESULT_DEGREES = 59.00F;
    public static final String TEST_DATA_STRING_RESULT_OUNCES = "0.53";
    public static final float TEST_DATA_FLOAT_RESULT_OUNCES = 0.52910995F;

    @Test
    public void convertKMtoMiles_returnMiles_STRING() {
        Assertions.assertEquals(TEST_DATA_STRING_RESULT_MILES, ConverterUtil.convertKMtoMiles(TEST_DATA_STRING));
        System.out.println("convertKMtoMiles_returnMiles_STRING: SUCCESS");
    }

    @Test
    public void convertCelsiusToFahrenheit_returnFahrenheit_STRING() {
        Assertions.assertEquals(TEST_DATA_STRING_RESULT_DEGREES, ConverterUtil.convertCelsiusToFahrenheit(TEST_DATA_STRING));
        System.out.println("convertCelsiusToFahrenheit_returnFahrenheit_STRING: SUCCESS");
    }

    @Test
    public void convertGramsToOunces_returnOunces_STRING() {
        Assertions.assertEquals(TEST_DATA_STRING_RESULT_OUNCES, ConverterUtil.convertGramsToOunces(TEST_DATA_STRING));
        System.out.println("convertGramsToOunces_returnOunces_STRING: SUCCESS");
    }

    @Test
    public void convertCelsiusToFahrenheit_returnFahrenheit_FLOAT() {
        Assertions.assertEquals(TEST_DATA_FLOAT_RESULT_DEGREES, ConverterUtil.convertCelsiusToFahrenheit(TEST_DATA_FLOAT));
        System.out.println("convertCelsiusToFahrenheit_returnFahrenheit_FLOAT: SUCCESS");
    }

    @Test
    public void convertKMtoMiles_returnMiles_FLOAT() {
        Assertions.assertEquals(TEST_DATA_FLOAT_RESULT_MILES, ConverterUtil.convertKMtoMiles(TEST_DATA_FLOAT));
        System.out.println("convertKMtoMiles_returnMiles_FLOAT: SUCCESS");
    }

    @Test
    public void convertGramsToOunces_returnOunces_FLOAT() {
        Assertions.assertEquals(TEST_DATA_FLOAT_RESULT_OUNCES, ConverterUtil.convertGramsToOunces(TEST_DATA_FLOAT));
        System.out.println("convertGramsToOunces_returnOunces_FLOAT: SUCCESS");
    }
}
