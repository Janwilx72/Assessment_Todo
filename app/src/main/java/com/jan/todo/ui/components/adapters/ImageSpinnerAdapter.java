package com.jan.todo.ui.components.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

import com.jan.todo.R;
import com.jan.todo.core.models.IconModel;

public class ImageSpinnerAdapter extends ArrayAdapter<IconModel>
{
    private final Context _context;
    private final List<IconModel> _icons;

    public ImageSpinnerAdapter(@NonNull Context context, @NonNull List<IconModel> icons)
    {
        super(context, R.layout.adapter_icon_images, icons);
        _context = context;
        _icons = icons;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(_context).inflate(R.layout.adapter_icon_images, parent, false);
        }

        final ImageView imageView = convertView.findViewById(R.id.ivIcon);
        imageView.setImageResource(_icons.get(position).getResourceId());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(_context).inflate(R.layout.adapter_icon_images_dropdown, parent, false);
        }

        final ImageView imageView = convertView.findViewById(R.id.ddIcon);
        imageView.setImageResource(_icons.get(position).getResourceId());

        final TextView textView = convertView.findViewById(R.id.tvDescription);
        textView.setText(_icons.get(position).getIconDescription());

        return convertView;
    }


}






















