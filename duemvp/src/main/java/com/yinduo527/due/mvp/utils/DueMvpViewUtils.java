package com.yinduo527.due.mvp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;

public final class DueMvpViewUtils {

    private DueMvpViewUtils() {
    }

    public static boolean isOnScreen(View view) {
        if (view == null) {
            return false;
        }

        ViewParent parent = view.getParent();

        while (parent != null) {
            if (parent instanceof ListView
                    || parent instanceof ScrollView
                    || parent instanceof RecyclerView
                    || parent instanceof HorizontalScrollView
                    || !(parent instanceof View)) {
                break;
            } else {
                parent = parent.getParent();
            }
        }

        if (parent == null) {
            return false;
        }

        // 在父view路径上如果出现非View的ViewParent，即可视为该View在屏幕上
        if (!(parent instanceof View)) {
            return true;
        }

        Context context = view.getContext();

        int screenLeft = 0;
        int screenRight = DueScreenUtils.getScreenWidth(context);
        int screenBottom = DueScreenUtils.getScreenHeight(context);

        // 排除掉statusbar跟actionbar占据的范围
        int screenTop = DueStatusBarUtils.getStatusBarHeight(view.getContext());
        if (context instanceof Activity) {
            screenTop += DueActionBarUtils.getActionBarHeight(context);
        }

        Rect rect = new Rect();
        boolean isGlobalVisible = view.getGlobalVisibleRect(rect);

        boolean isXOnScreen = rect.left >= screenLeft && rect.left <= screenRight || rect.right >= screenLeft && rect.right <= screenRight;
        boolean isYOnScreen = rect.top >= screenTop && rect.top <= screenBottom || rect.bottom >= screenTop && rect.bottom <= screenBottom;
        return isGlobalVisible && isXOnScreen && isYOnScreen;
    }
}
