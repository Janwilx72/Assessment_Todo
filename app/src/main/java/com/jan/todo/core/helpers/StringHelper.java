package com.jan.todo.core.helpers;

import android.text.Editable;

public class StringHelper
{
    public static boolean isNullOrWhiteSpace(final Editable editable)
    {
        return editable == null || isNullOrWhiteSpace(editable.toString());
    }

    public static boolean isNullOrWhiteSpace(String str)
    {
        if (str == null) {
            return true;
        }
        return str.trim().isEmpty();
    }

    public static String convertEditableToString(final Editable editable)
    {
        return editable == null
                ? null
                : editable.toString();
    }
}
