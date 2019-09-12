package com.yinduo527.due.mvp.sample;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yinduo527.due.mvp.DueBaseView;
import com.yinduo527.due.mvp.core.DuePresenter;

public class DueThirdView extends DueBaseView {
    public DueThirdView(DuePresenter presenter, int viewId) {
        super(presenter, viewId);
    }

    @Override
    protected View createView() {
        TextView textView = new TextView(getContext());
        textView.setText("third");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DueSampleFragmentActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return textView;
    }
}
