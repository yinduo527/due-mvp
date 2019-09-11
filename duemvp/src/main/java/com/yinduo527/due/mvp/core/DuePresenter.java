package com.yinduo527.due.mvp.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

public interface DuePresenter {

    /**
     * view变化的回调
     *
     * @param data View变化带来的影响结果
     * @param <D>
     */
    <D> void onViewChanged(int id, D data);

    /**
     * 数据请求回调
     *
     * @param data 数据请求返回结果
     * @param <D>
     */
    <D> void onModelChanged(int id, D data);

    /**
     * 提供给一个页面的基础请求Model在error的时候调用
     *
     * @param exception 错误信息以Exception形式传给Presenter
     */
    void onError(Exception exception);

    void add(DueView view);

    void add(DueModel model);

    DueView getView(int viewId);

    Activity getActivity();

    <D> void dispatchModelDataToView(int modelId, D data, int... viewIds);

    <D> void dispatchModelDataToModel(int modelId, D data, int... modelIds);

    <D> void dispatchViewDataToView(int viewId, D data, int... viewIds);

    <D> void dispatchViewDataToModel(int viewId, D data, int... modelIds);

    void start(int... modelIds);

    @Deprecated
    void load(DueModel... models);


    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    DuePage getPage();
}
