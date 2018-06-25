package com.example.pedrobraga.bancofinanca.Utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by pedro.braga on 19/04/2018.
 */

public class DateTypeConverter {


    @TypeConverter
    public static Date toDate(String value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static String toString(Date value) {
        return value == null ? null : String.valueOf(value.getTime());
    }


}
