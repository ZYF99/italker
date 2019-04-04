package com.zyf.italker.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.zyf.italker.common.app.Activity;

public class PermissionUtils {
// Storage Permissions

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     * <p>
     *      * @param activity
     *    
     */
    public static boolean verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
            return true;
        }else {
            return false;
        }
    }
}

