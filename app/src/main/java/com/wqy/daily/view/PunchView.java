package com.wqy.daily.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-4.
 */

public class PunchView extends ViewImpl {

    public static final String TAG = "PunchView";

    @BindView(R.id.punch_tv_content)
    TextView mTextView;

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

    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag(BusAction.SET_TAB_LAYOUT)}
    )
    public void setTabLayout(TabLayout tabLayout) {
        Log.d(TAG, "setTabLayout: ");
        tabLayout.addTab(tabLayout.newTab().setText(R.string.punch_underway));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.punch_finished));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.punch_recycle));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0 : mTextView.setText(R.string.punch_underway);
                        break;
                    case 1 : mTextView.setText(R.string.punch_finished);
                        break;
                    case 2 : mTextView.setText(R.string.punch_recycle);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
