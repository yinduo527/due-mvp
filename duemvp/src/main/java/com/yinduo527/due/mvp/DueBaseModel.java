package com.yinduo527.due.mvp;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.util.Log;

import com.yinduo527.due.mvp.core.DueModel;
import com.yinduo527.due.mvp.core.DuePage;
import com.yinduo527.due.mvp.core.DuePresenter;
import com.yinduo527.due.mvp.lifecycle.DueMvpLifeCycle;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnActivityResult;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnCreate;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnDestroy;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnPause;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnRequestPermissionsResult;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnResume;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnStart;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnStop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class DueBaseModel implements DueModel, DueMvpLifeCycle {
    private static final String TAG = "FoodBaseModel";

    private int modelId;

    private DuePresenter presenter;

    public DueBaseModel(DuePresenter presenter, int id) {
        this.modelId = id;
        this.presenter = presenter;
    }

    @Override
    public final <D> void onDataChanged(D data) {
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
                    Log.d(TAG, "InvocationTargetException", e);
                }
            } catch (IllegalAccessException e) {
                Log.d(TAG, getClass().toString());
                Log.d(TAG, "IllegalAccessException", e);
            }
        }
    }

    @Override
    public void setPresenter(DuePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public int getId() {
        return modelId;
    }

    protected Activity getActivity() {
        return presenter != null ? presenter.getActivity() : null;
    }

    protected Context getContext() {
        return getActivity();
    }

    protected final <D> void dispatchData(D data) {
        if (presenter == null) {
            return;
        }
        presenter.onModelChanged(getId(), data);
    }

    protected LoaderManager getLoaderManager() {
        LoaderManager loaderManager = null;
        DuePage page = presenter.getPage();
        if (page != null) {
            if (page instanceof Fragment) {
                loaderManager = ((Fragment) page).getLoaderManager();
            } else if (page instanceof Activity) {
                loaderManager = ((Activity) page).getLoaderManager();
            }
        }
        if (loaderManager == null && presenter.getActivity() != null) {
            loaderManager = presenter.getActivity().getLoaderManager();
        }
        return loaderManager;
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
