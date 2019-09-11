package com.yinduo527.due.mvp.sample;

import android.os.Handler;

import com.yinduo527.due.mvp.DueBaseModel;
import com.yinduo527.due.mvp.core.DuePresenter;

public class DueTextModel extends DueBaseModel {
    public DueTextModel(DuePresenter presenter, int id) {
        super(presenter, id);
    }

    @Override
    public void load() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DueChangeTextEvent event = new DueChangeTextEvent();
                event.text = "I come from DueTextModel.";
                dispatchData(event);
            }
        }, 3000);
    }
}
