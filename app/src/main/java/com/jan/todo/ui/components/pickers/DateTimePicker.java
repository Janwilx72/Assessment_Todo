package com.jan.todo.ui.components.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DateTimePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{

    private Calendar selectedDateTime = Calendar.getInstance();
    private DateTimeListener listener;

    public void setDateTimeListener(final DateTimeListener listener)
    {
        this.listener = listener;
    }

    public interface DateTimeListener
    {
        void onDateTimeSelected(final Calendar dateTime);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int year = selectedDateTime.get(Calendar.YEAR);
        int month = selectedDateTime.get(Calendar.MONTH);
        int day = selectedDateTime.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog
        return new DatePickerDialog(requireActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day)
    {
        // Set the date part in selectedDateTime
        selectedDateTime.set(Calendar.YEAR, year);
        selectedDateTime.set(Calendar.MONTH, month);
        selectedDateTime.set(Calendar.DAY_OF_MONTH, day);

        // Now that the date is set, show the TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this,
                selectedDateTime.get(Calendar.HOUR_OF_DAY),
                selectedDateTime.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute)
    {
        // Set the time part in selectedDateTime
        selectedDateTime.set(Calendar.HOUR_OF_DAY, hour);
        selectedDateTime.set(Calendar.MINUTE, minute);

        // Notify the activity of the selected date and time
        if (listener != null)
        {
            listener.onDateTimeSelected(selectedDateTime);
        }
    }
}
