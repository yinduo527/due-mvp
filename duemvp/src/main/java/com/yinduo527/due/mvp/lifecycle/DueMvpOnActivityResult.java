package com.yinduo527.due.mvp.lifecycle;

import android.content.Intent;

public class DueMvpOnActivityResult implements DueMvpLifeCycleEvent {
    public int requestCode;
    public int resultCode;
    public Intent data;

}
