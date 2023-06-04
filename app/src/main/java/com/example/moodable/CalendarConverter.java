package com.example.moodable;

import androidx.room.TypeConverter;

import java.util.GregorianCalendar;

public class CalendarConverter {
    @TypeConverter
    public static GregorianCalendar fromTimestampToCalendar(Long value) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(value);
        return gregorianCalendar;
    }

    @TypeConverter
    public static Long dateToTimestamp(GregorianCalendar date) {
        return date.getTimeInMillis();
    }
}
