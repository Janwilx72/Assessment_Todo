package com.jan.todo.ui.components.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jan.todo.R;
import com.jan.todo.core.helpers.StringHelper;

public class SortDialog extends DialogFragment
{
    private SortDialogListener _listener;

    public interface SortDialogListener
    {
        void SortClicked(final boolean isAscending, final String sortText);
        void SortByDefault();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_sort, null);

        initiateViews(view);

        dialogBuilder.setView(view);
        return dialogBuilder.create();
    }

    private void initiateViews(final View layoutView)
    {
        final Button btnAsc = layoutView.findViewById(R.id.btnSearchAsc);
        final Button btnDesc = layoutView.findViewById(R.id.btnSearchDesc);
        final Button btnSortClear = layoutView.findViewById(R.id.btnSortClear);
        final EditText etSortText = layoutView.findViewById(R.id.etSortText);

        btnAsc.setOnClickListener(view ->
        {
            final String sortText = StringHelper.convertEditableToString(etSortText.getText());
            _listener.SortClicked(true, sortText);
        });

        btnDesc.setOnClickListener(view ->
        {
            final String sortText = StringHelper.convertEditableToString(etSortText.getText());
            _listener.SortClicked(false, sortText);
        });

        btnSortClear.setOnClickListener(view ->
        {
            etSortText.setText("");
        });

    }

    public void setListener(final SortDialogListener listener)
    {
        _listener = listener;
    }

}
