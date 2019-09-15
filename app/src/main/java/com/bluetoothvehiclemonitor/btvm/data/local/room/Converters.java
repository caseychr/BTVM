package com.bluetoothvehiclemonitor.btvm.data.local.room;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static List<Double> fromString(String value) {
        Type listType = new TypeToken<List<Double>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromDoubleList(List<Double> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<Integer> fromStringtoIntegerList(String value) {
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromIntegerList(List<Integer> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


}
