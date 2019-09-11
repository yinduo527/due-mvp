package com.yinduo527.due.mvp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.Nullable;

import com.yinduo527.due.mvp.core.DuePage;
import com.yinduo527.due.mvp.core.DuePresenter;
import com.yinduo527.due.mvp.core.DueView;
import com.yinduo527.due.mvp.lifecycle.DueMvpLifeCycle;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnActivityResult;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnCreate;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnDestroy;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnPause;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnRequestPermissionsResult;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnResume;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnStart;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnStop;
import com.yinduo527.due.mvp.recyclerwrapper.DueMvpRecyclerItemWrapper;
import com.yinduo527.due.mvp.scrollevent.DueMvpScrollEvent;
import com.yinduo527.due.mvp.utils.DueMvpViewUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class DueBaseView implements DueView, DueMvpLifeCycle {

    private static final String TAG = "FoodBaseView";

    private View view;

    private int viewId;

    private DuePresenter presenter;

    public DueBaseView(DuePresenter presenter, int viewId) {
        this.presenter = presenter;
        this.viewId = viewId;
    }

    @Override
    public final <D> void onDataChanged(D data) {
        initView();

        // 不在屏幕上的View不处理滑动事件
        if (view != null && (!DueMvpViewUtils.isOnScreen(view)
//                || !view.isAttachedToWindow()
                || view.getParent() == null)) {
            if (data instanceof DueMvpScrollEvent) {
                return;
            }
        }

        if (data != null) {

            if (data.getClass() == Object.class) {
                throw new IllegalArgumentException("Please don't dispatch data whose Class type is Object !!!");
            }

            Class dataClazz = null;
            if (data instanceof Class) {
                dataClazz = (Class) data;
                data = null;
            } else {
                dataClazz = data.getClass();
            }

            Class clazz = getClass();
            Method method = null;
            try {
                method = clazz.getMethod("onDataChanged", dataClazz);
                method.invoke(this, data);
            } catch (NoSuchMethodException e) {
                Log.d(TAG, getClass().toString());
                Log.d(TAG, "NoSuchMethodException", e);
            } catch (InvocationTargetException e) {
                Throwable throwable = e.getTargetException();
                if (throwable instanceof RuntimeException) {
                    throw (RuntimeException) throwable;
                } else {
                    Log.d(TAG, getClass().toString());
                    Log.d(TAG, "NoSuchMethodException", e);
                }
            } catch (IllegalAccessException e) {
                Log.d(TAG, getClass().toString());
                Log.d(TAG, "NoSuchMethodException", e);
            }
        }
    }

    @Override
    public void setPresenter(DuePresenter presenter) {
        this.presenter = presenter;
    }

    protected abstract View createView();

    @Override
    @Nullable
    public View getView() {
        initView();
        return view;
    }

    @Override
    public int getId() {
        return viewId;
    }

    @Nullable
    protected Activity getActivity() {
        return presenter != null ? presenter.getActivity() : null;
    }

    @Nullable
    protected Context getContext() {
        return getActivity();
    }

    protected final <D> void dispatchData(D data) {
        if (presenter == null) {
            return;
        }
        presenter.onViewChanged(getId(), data);
    }

    protected void initView() {
        if (getActivity() == null) {
            return;
        }
        if (view == null) {
            view = createView();
            replace(viewId, view);
        }
    }

    protected void replace(int targetId, View source) {
        View target = null;
        DuePage page = presenter.getPage();
        if (page instanceof Activity) {
            target = ((Activity) page).findViewById(targetId);
        } else if (page instanceof Fragment) {
            View root = ((Fragment) page).getView();
            if (root != null) {
                target = root.findViewById(targetId);
            }
        }
        replace(target, source);
    }


    protected void replace(View target, View source) {
        if (target == null) {
            return;
        }
        if (source == null) {
            return;
        }
        final ViewParent viewParent = target.getParent();

        if (viewParent != null && viewParent instanceof ViewGroup) {
            final ViewGroup parent = (ViewGroup) viewParent;

            final int index = parent.indexOfChild(target);
            parent.removeViewInLayout(target);

            final int id = target.getId();
            source.setId(id);

            final ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
            if (layoutParams != null) {
                parent.addView(source, index, layoutParams);
            } else {
                parent.addView(source, index);
            }
        } else {
            Log.e(TAG, "ViewStub must have a non-null ViewGroup viewParent");
        }

    }

    public DueMvpRecyclerItemWrapper recyclerItemWrapper() {
        return new DueMvpRecyclerItemWrapper(presenter, this);
    }

    public void onDataChanged(DueMvpOnCreate onCreate) {}
    public void onDataChanged(DueMvpOnStart onStart) {}
    public void onDataChanged(DueMvpOnResume onResume) {}
    public void onDataChanged(DueMvpOnPause onPause) {}
    public void onDataChanged(DueMvpOnStop onStop) {}
    public void onDataChanged(DueMvpOnDestroy onDestroy) {}
    public void onDataChanged(DueMvpOnRequestPermissionsResult onRequestPermissionsResult) {}
    public void onDataChanged(DueMvpOnActivityResult onActivityResult) {}
}
