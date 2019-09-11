package com.yinduo527.due.mvp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public final class DueScreenUtils {
    private static int screenHeight = 0;
    private static int screenWidth = 0;

    private DueScreenUtils() {
    }

    public static int getScreenHeight(Context context) {
        if (screenHeight == 0 && context != null) {
            Resources resources = context.getResources();
            if (resources != null) {
                DisplayMetrics dm = resources.getDisplayMetrics();
                if (dm != null) {
                    screenWidth = dm.widthPixels;
                    screenHeight = dm.heightPixels;
                }
            }
        }
        return screenHeight;
    }

    public static int getScreenWidth(Context context) {
        if (screenWidth == 0 && context != null) {
            Resources resources = context.getResources();
            if (resources != null) {
                DisplayMetrics dm = resources.getDisplayMetrics();
                if (dm != null) {
                    screenWidth = dm.widthPixels;
                    screenHeight = dm.heightPixels;
                }
            }
        }
        return screenWidth;
    }

}
