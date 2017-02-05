package com.wqy.daily.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;

import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.BusAction;
import com.wqy.daily.R;
import com.wqy.daily.mvp.ViewImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-5.
 */

public class DiaryView extends ViewImpl {

    @BindView(R.id.diary_vp)
    ViewPager mViewPager;

    @Override
    public int getResId() {
        return R.layout.fragment_diary;
    }

    @Override
    public void created() {
        ButterKnife.bind(this, mRootView);
        RxBus.get().post(BusAction.HIDE_TAB_LAYOUT, "");
    }

    @Override
    public void start() {
        RxBus.get().register(this);
    }

    @Override
    public void stop() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_FAB)})
    public void setFab(FloatingActionButton fab) {
        fab.setImageResource(R.drawable.ic_create_white_24dp);
    }

    @Produce(tags = {@Tag(BusAction.SET_ACTIVITY_TITLE)})
    public String getTitle() {
        return getContext().getString(R.string.title_diary);
    }
}
