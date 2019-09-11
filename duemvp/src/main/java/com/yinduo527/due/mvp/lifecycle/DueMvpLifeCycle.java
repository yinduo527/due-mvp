package com.yinduo527.due.mvp.lifecycle;

public interface DueMvpLifeCycle {
    void onDataChanged(DueMvpOnCreate onCreate);

    void onDataChanged(DueMvpOnStart onStart);

    void onDataChanged(DueMvpOnResume onResume);

    void onDataChanged(DueMvpOnPause onPause);

    void onDataChanged(DueMvpOnStop onStop);

    void onDataChanged(DueMvpOnDestroy onDestroy);

    void onDataChanged(DueMvpOnRequestPermissionsResult onRequestPermissionsResult);

    void onDataChanged(DueMvpOnActivityResult onActivityResult);
}
