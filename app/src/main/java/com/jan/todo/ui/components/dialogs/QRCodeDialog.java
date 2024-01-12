package com.jan.todo.ui.components.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jan.todo.R;
import com.jan.todo.core.database.entities.TodoItemEntity;
import com.jan.todo.core.helpers.QRCodeHelper;

import java.security.NoSuchAlgorithmException;

public class QRCodeDialog extends DialogFragment
{
    private final Bitmap _bitmap;
    private final TodoItemEntity _entity;

    public QRCodeDialog(final Bitmap bitmap, final TodoItemEntity entity)
    {
        _bitmap = bitmap;
        _entity = entity;
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

        final TextView tvHash = layoutView.findViewById(R.id.tvHash);
        QRCodeHelper qrCodeHelper = new QRCodeHelper();
        try
        {
            final String hash = qrCodeHelper.generateHashFromString(_entity.toString());
            tvHash.setText(hash);
        }
        catch (NoSuchAlgorithmException e)
        {
            tvHash.setText("Error generating Hash");
        }


    }
}
