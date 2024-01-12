package com.jan.todo.core.helpers;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateHelper
{
    public String convertLongToDate(final long timeInMillis)
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
        return sdf.format(timeInMillis);
    }
}
