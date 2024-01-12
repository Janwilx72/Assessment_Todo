package com.jan.todo.core.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper
{
    public boolean checkPermission(final Context context)
    {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestCameraPermission(final Activity activity)
    {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 101);
    }
}
