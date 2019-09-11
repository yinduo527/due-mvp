package com.yinduo527.due.mvp.utils;

import android.content.Context;
import android.util.TypedValue;

public final class DueActionBarUtils {
    private static int actionBarHeight = 0;

    private DueActionBarUtils() {
    }

    public static int getActionBarHeight(Context context) {
        if (actionBarHeight == 0) {
            if (context != null) {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
                actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
            }
        }
        return actionBarHeight;
    }
}
