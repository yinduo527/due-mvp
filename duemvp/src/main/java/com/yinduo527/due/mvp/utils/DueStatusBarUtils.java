package com.yinduo527.due.mvp.utils;

import android.content.Context;

public final class DueStatusBarUtils {

    private static int statusBarHeight = 0;

    private DueStatusBarUtils() {
    }

    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight == 0) {
            int id = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (id != 0) {
                statusBarHeight = context.getResources().getDimensionPixelSize(id);
            }
        }
        return statusBarHeight;
    }
}
