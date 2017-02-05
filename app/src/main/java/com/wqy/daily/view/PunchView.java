package com.wqy.daily.view;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.BusAction;
import com.wqy.daily.R;
import com.wqy.daily.adapter.ListPagerAdapter;
import com.wqy.daily.mvp.ViewImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by wqy on 17-2-4.
 */

public class PunchView extends ViewImpl {

    public static final String TAG = "PunchView";

    @BindView(R.id.punch_vp)
    ViewPager mViewPager;

    @Override
    public Context getContext() {
        return mRootView.getContext();
    }

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
    public void bindEvent() {

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
                getContext().getString(R.string.punch_underway),
                getContext().getString(R.string.punch_finished),
                getContext().getString(R.string.punch_recycle)
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
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText(R.string.punch_underway));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.punch_finished));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.punch_recycle));
        setViewPager();
        tabLayout.setupWithViewPager(mViewPager);
    }
}
