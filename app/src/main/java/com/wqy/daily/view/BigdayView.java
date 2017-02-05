package com.wqy.daily.view;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.BusAction;
import com.wqy.daily.R;
import com.wqy.daily.adapter.ListPagerAdapter;
import com.wqy.daily.mvp.ViewImpl;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-5.
 */

public class BigdayView extends ViewImpl {

    public static final String TAG = "BigdayView";

    @BindView(R.id.bigday_vp)
    ViewPager mViewPager;

    @Override
    public int getResId() {
        return R.layout.fragment_bigday;
    }

    @Override
    public void created() {
        ButterKnife.bind(this, mRootView);
    }

    @Override
    public void start() {
        RxBus.get().register(this);
    }

    @Override
    public void stop() {
        RxBus.get().unregister(this);
    }

    public void setViewPager() {
        List<View> views = Arrays.asList(
                LayoutInflater.from(getContext()).inflate(R.layout.view_test, null),
                LayoutInflater.from(getContext()).inflate(R.layout.view_test, null)
        );
        views.get(0).setBackgroundColor(Color.RED);
        views.get(1).setBackgroundColor(Color.GREEN);
        List<String> titles = Arrays.asList(
                getContext().getString(R.string.tab_backward),
                getContext().getString(R.string.tab_forward)
        );
        ListPagerAdapter adapter = new ListPagerAdapter(views, titles);
        mViewPager.setAdapter(adapter);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag(BusAction.SET_TAB_LAYOUT)}
    )
    public void setTabLayout(TabLayout tabLayout) {
        Log.d(TAG, "setTabLayout: ");
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        setViewPager();
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Produce(tags = {@Tag(BusAction.SET_ACTIVITY_TITLE)})
    public String getTitle() {
        return getContext().getString(R.string.title_bigday);
    }
}
