package com.example.nhoxb.myflicks.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by tom on 6/28/18.
 */
public final class DeviceUtils {
    public static boolean isPortraitOrientation(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
