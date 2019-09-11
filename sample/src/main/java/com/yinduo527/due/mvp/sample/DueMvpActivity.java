package com.yinduo527.due.mvp.sample;

import android.os.Bundle;

import com.yinduo527.due.mvp.DueBaseActivity;

public class DueMvpActivity extends DueBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_due_mvp_sample);

        presenter.add(new DueFirstView(presenter, R.id.first));
        presenter.add(new DueSecondView(presenter, R.id.second));
        presenter.add(new DueThirdView(presenter, R.id.third));


        presenter.add(new DueTextModel(presenter, R.id.first_model));

        presenter.start(R.id.first_model);

    }
}
