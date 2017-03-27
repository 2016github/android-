package com.king.baseproject.util;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

/**
 * Created by king on 2017/3/23.
 */

public class PremissionUtil {
    public static void requestPermission(Activity activity, int requestId,
                                         String permission, boolean finishActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // Display a dialog with rationale.
        } else {
            // Location permission has not been granted yet, request it.
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);

        }
    }

}
