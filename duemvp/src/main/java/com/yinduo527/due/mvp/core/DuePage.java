package com.yinduo527.due.mvp.core;

import android.app.Activity;

public interface DuePage {

    Activity getActivity();

    /**
     * 提供给一个页面的基础请求Model在error的时候调用
     *
     * @param exception 错误信息以Exception形式传给Presenter
     */
    void onPageError(Exception exception);

}
