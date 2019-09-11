package com.yinduo527.due.mvp.sample;

import android.view.View;
import android.widget.TextView;

import com.yinduo527.due.mvp.DueBaseView;
import com.yinduo527.due.mvp.core.DuePresenter;

public class DueSecondView extends DueBaseView {
    public DueSecondView(DuePresenter presenter, int viewId) {
        super(presenter, viewId);
    }

    @Override
    protected View createView() {
        TextView textView = new TextView(getContext());
        textView.setText("second");
        return textView;
    }
}
