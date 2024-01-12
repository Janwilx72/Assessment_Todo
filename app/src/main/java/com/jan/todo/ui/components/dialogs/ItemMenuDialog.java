package com.jan.todo.ui.components.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
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
import com.jan.todo.core.helpers.DateHelper;
import com.jan.todo.core.helpers.IconHelper;
import com.jan.todo.core.models.IconModel;

import java.util.stream.Collectors;

public class ItemMenuDialog extends DialogFragment
{
    public interface ItemMenuDialogListener
    {
        void onEditClick(final TodoItemEntity itemEntity);
        void onDeleteClick(final TodoItemEntity itemEntity);
        void onShareClick(final TodoItemEntity itemEntity);
    }

    private TodoItemEntity _entity;
    private ItemMenuDialogListener _listener;
    private final DateHelper _dateHelper;

    public void setTodoItemEntity(final TodoItemEntity todoItem)
    {
        _entity = todoItem;
    }
    public void setListener(final ItemMenuDialogListener listener)
    {
        _listener = listener;
    }

    public ItemMenuDialog(final TodoItemEntity todoItem)
    {
        _entity = todoItem;
        _dateHelper = new DateHelper();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_item_menu, null);

        initiateViews(view);

        dialogBuilder.setView(view);
        return dialogBuilder.create();
    }

    private void initiateViews(final View layoutView)
    {
        final Button btnDelete = layoutView.findViewById(R.id.btnDelete);
        final Button btnEdit = layoutView.findViewById(R.id.btnEdit);
        final Button btnShare = layoutView.findViewById(R.id.btnShare);

        final ImageView ivIcon = layoutView.findViewById(R.id.ivIcon);
        final TextView tvTitle = layoutView.findViewById(R.id.tvTitle);
        final TextView tvDescription = layoutView.findViewById(R.id.tvDescription);
        final TextView tvDueDate = layoutView.findViewById(R.id.tvDueDate);

        final IconModel icon = IconHelper.getIcons().stream().filter(x -> x.getIconId() == _entity.getIconId()).collect(Collectors.toList()).get(0);
        ivIcon.setImageResource(icon.getResourceId());

        tvTitle.setText(_entity.getTitle());
        tvDescription.setText(_entity.getBody());
        tvDueDate.setText(_dateHelper.convertLongToDate(_entity.getDueDate()));

        btnDelete.setOnClickListener(view ->
        {
            _listener.onDeleteClick(_entity);
        });

        btnEdit.setOnClickListener(view ->
        {
            _listener.onEditClick(_entity);
        });

        btnShare.setOnClickListener(view ->
        {
            _listener.onShareClick(_entity);
        });
    }

}
