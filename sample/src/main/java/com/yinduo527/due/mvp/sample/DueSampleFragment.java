package com.yinduo527.due.mvp.sample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yinduo527.due.mvp.DueBaseFragment;

public class DueSampleFragment extends DueBaseFragment {

    public static Fragment newInstance() {
        return new DueSampleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.activity_due_mvp_sample, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.add(new DueFirstView(presenter, R.id.first));
        presenter.add(new DueSecondView(presenter, R.id.second));
        presenter.add(new DueThirdView(presenter, R.id.third));

        presenter.add(new DueTextModel(presenter, R.id.first_model));

        presenter.start(R.id.first_model);
    }
}
