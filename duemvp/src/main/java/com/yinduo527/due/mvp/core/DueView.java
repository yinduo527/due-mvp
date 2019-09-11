package com.yinduo527.due.mvp.core;

import android.view.View;

public interface DueView {

    void setPresenter(DuePresenter presenter);

    <D> void onDataChanged(D data);

    View getView();

    int getId();
}
