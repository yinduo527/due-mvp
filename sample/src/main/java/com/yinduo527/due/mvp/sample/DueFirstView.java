package com.yinduo527.due.mvp.sample;

import android.view.View;
import android.widget.TextView;

import com.yinduo527.due.mvp.DueBaseView;
import com.yinduo527.due.mvp.core.DuePresenter;

public class DueFirstView extends DueBaseView {
    public DueFirstView(DuePresenter presenter, int viewId) {
        super(presenter, viewId);
    }

    @Override
    protected View createView() {
        TextView textView = new TextView(getContext());
        textView.setText("first");
        return textView;
    }

    public void onDataChanged(DueChangeTextEvent event) {
        View view = getView();
        if (view != null) {
            ((TextView) view).setText(event.text);
        }
    }
}
