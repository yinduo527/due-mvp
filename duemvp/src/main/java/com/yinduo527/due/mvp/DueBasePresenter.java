package com.yinduo527.due.mvp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.yinduo527.due.mvp.core.DueModel;
import com.yinduo527.due.mvp.core.DuePage;
import com.yinduo527.due.mvp.core.DuePresenter;
import com.yinduo527.due.mvp.core.DueView;
import com.yinduo527.due.mvp.lifecycle.DueMvpLifeCycleEvent;
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

public class DueBasePresenter implements DuePresenter {

    private static final String TAG = "FoodBasePresenter";

    private SparseArray<DueView> mvpViewMap = new SparseArray<>();
    private SparseArray<DueModel> mvpModelMap = new SparseArray<>();
    private DuePage page;

    public DueBasePresenter(DuePage page) {
        if (!(page instanceof Fragment) && !(page instanceof Activity)) {
            throw new IllegalArgumentException("FoodMvpPage must be implemented by Activity or Fragment");
        }
        this.page = page;
    }

    @Override
    public <D> void onViewChanged(int id, D data) {
        onDataChanged(id,data,"onViewChanged");
    }

    @Override
    public <D> void onModelChanged(int id, D data) {
        onDataChanged(id, data, "onModelChanged");
    }

    private <D> void onDataChanged(int id, D data, String methodName) {
        if (data != null && page != null) {

            if (data.getClass() == Object.class) {
                throw new IllegalArgumentException("Please don't dispatch data whose Class type is Object !!!");
            }

            Class dataClazz = data.getClass();
            Class clazz = page.getClass();
            Method method = null;
            try {
                method = clazz.getMethod(methodName, int.class, dataClazz);
                method.invoke(page, id, data);
            } catch (NoSuchMethodException e) {
                Log.d(TAG, getClass().toString());
                Log.d(TAG, "NoSuchMethodException", e);

                for (int i = 0; i < mvpViewMap.size(); i++) {
                    DueView view = mvpViewMap.valueAt(i);
                    view.onDataChanged(data);
                }

                for (int i = 0; i < mvpModelMap.size(); i++) {
                    DueModel model = mvpModelMap.valueAt(i);
                    model.onDataChanged(data);
                }

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
    public void onError(Exception exception) {
        if (page != null) {
            page.onPageError(exception);
        }
    }

    @Override
    public void add(DueView view) {
        mvpViewMap.put(view.getId(), view);
    }

    @Override
    public void add(DueModel model) {
        mvpModelMap.put(model.getId(), model);

    }

    @Override
    public DueView getView(int viewId) {
        if (mvpViewMap != null && mvpViewMap.indexOfKey(viewId) >= 0) {
            return mvpViewMap.get(viewId);
        }
        return null;
    }

    @Override
    public Activity getActivity() {
        return page != null ? page.getActivity() : null;
    }

    /**
     * 将model获取到的数据D，传给指定id的view，并调用指定view的onDataChanged()
     *
     * @param modelId model的id
     * @param data    获取到的数据
     * @param viewIds 指定id的view
     * @param <D>     获取到的数据的限定类型
     */
    @Override
    public <D> void dispatchModelDataToView(int modelId, D data, int... viewIds) {
        for (int id : viewIds) {
            DueView view = mvpViewMap.get(id);
            if (view != null) {
                if (data != null) {
                    view.onDataChanged(data);
                }
            }
        }
    }

    @Override
    public <D> void dispatchModelDataToModel(int modelId, D data, int... modelIds) {
        for (int id : modelIds) {
            DueModel model = mvpModelMap.get(id);
            if (model != null) {
                if (data != null) {
                    model.onDataChanged(data);
                }
            }
        }
    }

    /**
     * 将view获取到的数据D，传给指定id的view，并调用指定view的onDataChanged()
     *
     * @param viewId  调用目标view的预设id
     * @param data    要传递的数据
     * @param viewIds 想要调用的目标view的id集合
     * @param <D>     要传递的数据的限定类型
     */
    @Override
    public <D> void dispatchViewDataToView(int viewId, D data, int... viewIds) {
        for (int id : viewIds) {
            DueView view = mvpViewMap.get(id);
            if (view != null) {
                if (data != null) {
                    view.onDataChanged(data);
                }
            }
        }
    }

    @Override
    public <D> void dispatchViewDataToModel(int viewId, D data, int... modelIds) {
        for (int id : modelIds) {
            DueModel model = mvpModelMap.get(id);
            if (model != null) {
                if (data != null) {
                    model.onDataChanged(data);
                }
            }
        }
    }

    @Override
    public void start(int... modelIds) {

        for (int id : modelIds) {
            // TODO: 2019/3/31 这里实际上会阻塞
            DueModel model = mvpModelMap.get(id);
            if (model != null) {
                model.load();
            }
        }
    }

    @Override
    public void load(DueModel... models) {
        for (DueModel model : models) {
            model.load();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DueMvpOnCreate onCreate = new DueMvpOnCreate();
        onCreate.savedInstanceState = savedInstanceState;
        dispatchLifeCycle(onCreate);
    }

    @Override
    public void onStart() {
        dispatchLifeCycle(new DueMvpOnStart());
    }

    @Override
    public void onResume() {
        dispatchLifeCycle(new DueMvpOnResume());
    }

    @Override
    public void onPause() {
        dispatchLifeCycle(new DueMvpOnPause());
    }

    @Override
    public void onStop() {
        dispatchLifeCycle(new DueMvpOnStop());
    }

    @Override
    public void onDestroy() {
        dispatchLifeCycle(new DueMvpOnDestroy());

        for (int i = 0; i < mvpViewMap.size(); i++) {
            DueView view = mvpViewMap.valueAt(i);
            view.setPresenter(null);
        }

        for (int i = 0; i < mvpModelMap.size(); i++) {
            DueModel model = mvpModelMap.valueAt(i);
            model.setPresenter(null);
        }

        mvpModelMap.clear();
        mvpViewMap.clear();
        page = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        DueMvpOnActivityResult onActivityResult = new DueMvpOnActivityResult();
        onActivityResult.requestCode = requestCode;
        onActivityResult.resultCode = resultCode;
        onActivityResult.data = data;
        dispatchLifeCycle(onActivityResult);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        DueMvpOnRequestPermissionsResult onRequestPermissionsResult = new DueMvpOnRequestPermissionsResult();
        onRequestPermissionsResult.requestCode = requestCode;
        onRequestPermissionsResult.permissions = permissions;
        onRequestPermissionsResult.grantResults = grantResults;
        dispatchLifeCycle(onRequestPermissionsResult);
    }

    @Override
    public DuePage getPage() {
        return page;
    }

    private <T extends DueMvpLifeCycleEvent> void dispatchLifeCycle(T lifeCycleEvent) {
        for (int i = 0; i < mvpViewMap.size(); i++) {
            DueView view = mvpViewMap.valueAt(i);
            view.onDataChanged(lifeCycleEvent);
        }

        for (int i = 0; i < mvpModelMap.size(); i++) {
            DueModel model = mvpModelMap.valueAt(i);
            model.onDataChanged(lifeCycleEvent);
        }
    }
}
