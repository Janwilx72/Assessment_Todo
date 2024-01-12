package com.jan.todo.ui.components.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jan.todo.R;

public class QRCodeDialog extends DialogFragment
{
    private final Bitmap _bitmap;

    public QRCodeDialog(final Bitmap bitmap)
    {
        _bitmap = bitmap;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_qrcode, null);

        initiateViews(view);

        dialogBuilder.setView(view);
        return dialogBuilder.create();
    }

    private void initiateViews(final View layoutView)
    {
        final Button btnClose = layoutView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(view ->
        {
            this.dismiss();
        });

        final ImageView ivQRCode = layoutView.findViewById(R.id.ivQRCode);
        ivQRCode.setImageBitmap(_bitmap);
    }
}
