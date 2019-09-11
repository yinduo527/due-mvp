package com.yinduo527.due.mvp.core;

public interface DueModel {

    void setPresenter(DuePresenter presenter);

    <D> void onDataChanged(D data);

    void load();

    int getId();

}
