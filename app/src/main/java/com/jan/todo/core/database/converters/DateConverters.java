package com.jan.todo.core.database.converters;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverters
{
    @TypeConverter
    public static Date fromTimestamp(final Long value)
    {
        return value == null
                ? null
                : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(final Date date)
    {
        return date == null
                ? null
                : date.getTime();
    }
}
