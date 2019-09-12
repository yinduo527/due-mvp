package com.yinduo527.due.mvp.sample;

import android.app.Activity;
import android.os.Bundle;

public class DueSampleFragmentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, DueSampleFragment.newInstance()).commit();
    }
}
