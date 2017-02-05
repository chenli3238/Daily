package com.wqy.daily.view;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;

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
 * Created by wqy on 17-2-4.
 */

public class PunchView extends ViewImpl {

    public static final String TAG = "PunchView";

    @BindView(R.id.punch_vp)
    ViewPager mViewPager;

    @Override
    public int getResId() {
        return R.layout.fragment_punch;
    }

    @Override
    public void created() {
        Log.d(TAG, "created: ");
        ButterKnife.bind(this, mRootView);
    }

    @Override
    public void destroy() {
        Log.d(TAG, "destroy: ");

    }

    @Override
    public void start() {
        Log.d(TAG, "start: ");
        RxBus.get().register(this);
    }

    @Override
    public void stop() {
        Log.d(TAG, "stop: ");
        RxBus.get().unregister(this);
    }

    public void setViewPager() {
        List<View> views = Arrays.asList(
                LayoutInflater.from(getContext()).inflate(R.layout.view_test, null),
                LayoutInflater.from(getContext()).inflate(R.layout.view_test, null),
                LayoutInflater.from(getContext()).inflate(R.layout.view_test, null)
        );
        views.get(0).setBackgroundColor(Color.RED);
        views.get(1).setBackgroundColor(Color.GREEN);
        views.get(2).setBackgroundColor(Color.BLUE);
        List<String> titles = Arrays.asList(
                getContext().getString(R.string.tab_underway),
                getContext().getString(R.string.tab_finished),
                getContext().getString(R.string.tab_recycle)
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
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_underway));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_finished));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_recycle));
        setViewPager();
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_FAB)})
    public void setFab(FloatingActionButton fab) {
        fab.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setDuration(1000)
                .start();
        fab.setImageResource(R.drawable.ic_turned_in_white_24dp);
    }

    @Produce(tags = {@Tag(BusAction.SET_ACTIVITY_TITLE)})
    public String getTitle() {
        return getContext().getString(R.string.title_punch);
    }
}
