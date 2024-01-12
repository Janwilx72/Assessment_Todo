package com.jan.todo.ui.components.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.jan.todo.R;
import com.jan.todo.core.database.entities.TodoItemEntity;
import com.jan.todo.core.helpers.DateHelper;
import com.jan.todo.core.helpers.IconHelper;
import com.jan.todo.core.helpers.StringHelper;
import com.jan.todo.core.models.IconModel;
import com.jan.todo.ui.components.adapters.ImageSpinnerAdapter;
import com.jan.todo.ui.components.pickers.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TodoAddDialog extends DialogFragment implements DateTimePicker.DateTimeListener
{
    public interface TodoDialogListener
    {
        void onCreateClick(final TodoItemEntity entity);
        void onCancelClick();
    }

    private TodoDialogListener listener;
    private TextView tvDueDate;
    private EditText etTitle;
    private EditText etDescription;
    Spinner _spIcons;
    private TodoItemEntity todoEntity;
    private DateHelper _dateHelper;

    public TodoAddDialog(final TodoItemEntity entity)
    {
        todoEntity = entity;
        _dateHelper = new DateHelper();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        if (todoEntity == null)
            todoEntity = new TodoItemEntity();

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_todo, null);

        initiateViews(view);

        dialogBuilder.setView(view);
        return dialogBuilder.create();
    }


    public void initiateViews(final View layoutView)
    {
        tvDueDate = layoutView.findViewById(R.id.tvDueDate);
        etTitle = layoutView.findViewById(R.id.etTitle);
        etDescription = layoutView.findViewById(R.id.etDescription);

        etTitle.setText(todoEntity.getTitle());
        etDescription.setText(todoEntity.getBody());
        if (todoEntity.getDueDate() != 0)
        {
            tvDueDate.setText(_dateHelper.convertLongToDate(todoEntity.getDueDate()));
        }

        _spIcons = layoutView.findViewById(R.id.spIcons);
        ImageSpinnerAdapter adapter = new ImageSpinnerAdapter(requireActivity(), IconHelper.getIcons());
        _spIcons.setAdapter(adapter);
        int selectedItem = todoEntity.getIconId() == 0 ? 0 : todoEntity.getIconId() - 1;
        _spIcons.setSelection(selectedItem);

        final Button btnChangeDueDate = layoutView.findViewById(R.id.btnChangeDueDate);
        btnChangeDueDate.setOnClickListener((view) ->
        {
            final DateTimePicker dateTimePicker = new DateTimePicker();
            dateTimePicker.setDateTimeListener(this);
            dateTimePicker.show(getParentFragmentManager(), "DateTimePicker");
        });

        final Button btnCreate = layoutView.findViewById(R.id.btnCreate);
        if (todoEntity.getId() != 0)
        {
            btnCreate.setText("Edit");
        }

        btnCreate.setOnClickListener(view ->
        {
            final String title = StringHelper.convertEditableToString(etTitle.getText());
            final String description = StringHelper.convertEditableToString(etDescription.getText());
            if (StringHelper.isNullOrWhiteSpace(title))
            {
                Toast.makeText(requireActivity(), "Title can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (todoEntity.getDueDate() < System.currentTimeMillis())
            {
                Toast.makeText(requireActivity(), "Due date must be in the future", Toast.LENGTH_SHORT).show();
                return;
            }

            if (listener != null)
            {
                todoEntity.setTitle(title);
                todoEntity.setBody(description);

                final IconModel icon = (IconModel)_spIcons.getSelectedItem();

                todoEntity.setIconId(icon.getIconId());

                listener.onCreateClick(todoEntity);
            }
        });

        final Button btnCancel = layoutView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view ->
        {
            if (this.listener != null)
            {
                listener.onCancelClick();
            }

            if (TodoAddDialog.this.getDialog() != null)
            {
                TodoAddDialog.this.getDialog().cancel();
            }
        });
    }

    @Override
    public void onDateTimeSelected(final Calendar dateTime)
    {
        final long selectedTime = dateTime.getTimeInMillis();
        todoEntity.setDueDate(selectedTime);

        final SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
        final String date = format.format(dateTime.getTime());
        tvDueDate.setText(date);
    }

    public void setListener(final TodoDialogListener listener)
    {
        this.listener = listener;
    }
}
