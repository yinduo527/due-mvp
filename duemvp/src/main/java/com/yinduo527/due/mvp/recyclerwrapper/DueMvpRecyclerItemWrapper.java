package com.yinduo527.due.mvp.recyclerwrapper;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.yinduo527.due.mvp.DueBaseView;
import com.yinduo527.due.mvp.core.DuePresenter;
import com.yinduo527.due.mvp.core.DueView;
import com.yinduo527.due.mvp.lifecycle.DueMvpLifeCycle;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnActivityResult;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnCreate;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnDestroy;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnPause;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnRequestPermissionsResult;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnResume;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnStart;
import com.yinduo527.due.mvp.lifecycle.DueMvpOnStop;
import com.yinduo527.due.mvp.scrollevent.DueMvpScrollEvent;
import com.yinduo527.due.mvp.utils.DueMvpViewUtils;
import com.yinduo527.due.mvp.utils.DueScreenUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 使用mvp实现的recyclerViewItem
 */
public class DueMvpRecyclerItemWrapper extends RecyclerView.ViewHolder implements DueView, DueMvpLifeCycle {

    private DueBaseView foodBaseView;

    private List<Object> dataList = new LinkedList<>();

    private boolean hasInitView = false;

    public DueMvpRecyclerItemWrapper(DuePresenter presenter, DueBaseView foodBaseView) {
        super(new FrameLayout(presenter.getActivity()));
        itemView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.foodBaseView = foodBaseView;
    }

    @Override
    public void setPresenter(DuePresenter presenter) {
        foodBaseView.setPresenter(presenter);
    }

    /**
     * 把dataList里保存的各种数据和事件，都传入模块视图里，去更新模块视图
     */
    public void bindItemView() {
        if (dataList != null && dataList.size() > 0) {
            initView();
            for (Object data : dataList) {
                foodBaseView.onDataChanged(data);
            }
            dataList.clear();
        }
        //TODO 尝试优化FoodMvpRecyclerItemWrapper，使RecyclerView在使用的时候只加载在第一屏的内容，目前效果不是特别理想
        //TODO 这段注释先留着，后面再做优化的时候可能会用到
        View view = foodBaseView.getView();
        if (view.getVisibility() == View.VISIBLE) {
            view.measure(View.MeasureSpec.makeMeasureSpec(DueScreenUtils.getScreenWidth(view.getContext()), View.MeasureSpec.EXACTLY), ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setMinimumHeight(foodBaseView.getView().getMeasuredHeight());
        }
    }

    @Override
    public <D> void onDataChanged(D data) {
        if (itemView != null && (!DueMvpViewUtils.isOnScreen(itemView)
//                && !itemView.isAttachedToWindow()
                && !(data instanceof DueMvpLifeCycle))) {
            if (!(data instanceof DueMvpScrollEvent)) {
                dataList.add(data);
            }
            return;
        }

        initView();
        foodBaseView.onDataChanged(data);
    }

    @Override
    public View getView() {
        return itemView;
    }

    @Override
    public int getId() {
        return foodBaseView.getId();
    }

    protected void initView() {
        if (hasInitView) {
            return;
        }
        FrameLayout containerView = (FrameLayout) itemView;
        if (containerView.getChildCount() == 0) {
            containerView.addView(foodBaseView.getView());
            hasInitView = true;
        }
    }

    @Override
    public void onDataChanged(DueMvpOnCreate onCreate) {
        foodBaseView.onDataChanged(onCreate);
    }

    @Override
    public void onDataChanged(DueMvpOnStart onStart) {
        foodBaseView.onDataChanged(onStart);
    }

    @Override
    public void onDataChanged(DueMvpOnResume onResume) {
        foodBaseView.onDataChanged(onResume);
    }

    @Override
    public void onDataChanged(DueMvpOnPause onPause) {
        foodBaseView.onDataChanged(onPause);
    }

    @Override
    public void onDataChanged(DueMvpOnStop onStop) {
        foodBaseView.onDataChanged(onStop);
    }

    @Override
    public void onDataChanged(DueMvpOnDestroy onDestroy) {
        foodBaseView.onDataChanged(onDestroy);
    }

    @Override
    public void onDataChanged(DueMvpOnRequestPermissionsResult onRequestPermissionsResult) {
        foodBaseView.onDataChanged(onRequestPermissionsResult);
    }

    @Override
    public void onDataChanged(DueMvpOnActivityResult onActivityResult) {
        foodBaseView.onDataChanged(onActivityResult);
    }

    public DueBaseView getWrappedMVPView() {
        return foodBaseView;
    }
}