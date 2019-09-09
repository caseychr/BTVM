package com.bluetoothvehiclemonitor.btvm.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtil {

    //public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";

    public static final String SIMPLE_DATE_FORMAT = "MM-dd-yyyy HH:mm:ss";


    public static String getStringFromCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        String strDate = dateFormat.format(new java.util.Date());
        return strDate;
    }
}
