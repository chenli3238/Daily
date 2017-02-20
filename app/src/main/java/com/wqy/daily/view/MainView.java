package com.wqy.daily.view;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.TestActivity;
import com.wqy.daily.presenter.BigdayFragment;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.presenter.DiaryFragment;
import com.wqy.daily.presenter.MemoFragment;
import com.wqy.daily.presenter.PunchFragment;
import com.wqy.daily.R;
import com.wqy.daily.mvp.ViewImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-4.
 */

public class MainView extends ViewImpl {

    public static final String TAG = "MainView";

    @BindView(R.id.main_fab)
    public FloatingActionButton mFab;

    @BindView(R.id.nav_view)
    public NavigationView mNavigationView;

    @BindView(R.id.main_toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawerLayout;

    @BindView(R.id.punch_tab_layout)
    public TabLayout mTabLayout;

    @Override
    public int getResId() {
        return R.layout.activity_main;
    }

    @Override
    public int getMenuId() {
        return R.menu.main;
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.content_main;
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } if (id == R.id.main_test) {
            RxBus.get().post(BusAction.START_ACTIVITY, TestActivity.class);
        } if (id == R.id.main_delete_all) {
            RxBus.get().post(BusAction.DELETE_ALL, "");
        }
        return super.onMenuItemSelected(item);
    }

    @Override
    public void created() {
        Log.d(TAG, "created: ");
        ButterKnife.bind(this, mRootView);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                (Activity) getContext(), mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        Log.d(TAG, "destroy: ");
        RxBus.get().unregister(this);
    }

    @Override
    public void bindEvent() {
        mNavigationView.setNavigationItemSelectedListener(MainView.this::onNavigationItemSelected);
    }

    private void setFragment(String tag) {
        RxBus.get().post(BusAction.SET_FRAGMENT_IN_MAIN, tag);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_punch) {
            setFragment(PunchFragment.TAG);
        } else if (id == R.id.nav_diary) {
            setFragment(DiaryFragment.TAG);
        } else if (id == R.id.nav_memo) {
            setFragment(MemoFragment.TAG);
        } else if (id == R.id.nav_bidday) {
            setFragment(BigdayFragment.TAG);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Produce(tags = {@Tag(BusAction.SET_TAB_LAYOUT)})
    public TabLayout getTabLayout() {
        Log.d(TAG, "getToolbar: ");
        return mTabLayout;
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag(BusAction.HIDE_TAB_LAYOUT)}
    )
    public void hideTabLayout(Object o) {
        Log.d(TAG, "hideTabLayout: ");
        mTabLayout.setVisibility(View.GONE);
    }

    @Produce(tags = {@Tag(BusAction.SET_FAB)})
    public FloatingActionButton getFab() {
        Log.d(TAG, "getFab: ");
        return mFab;
    }
}
