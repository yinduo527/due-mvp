package com.yinduo527.due.mvp.lifecycle;

public class DueMvpOnRequestPermissionsResult implements DueMvpLifeCycleEvent {
    public int requestCode;
    public String[] permissions;
    public int[] grantResults;
}
