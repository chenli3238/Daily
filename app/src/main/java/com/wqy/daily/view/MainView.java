package com.wqy.daily.view;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
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

    @BindView(R.id.fab)
    public FloatingActionButton mFab;

    @BindView(R.id.nav_view)
    public NavigationView mNavigationView;

    @BindView(R.id.toolbar)
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
        }
        return super.onMenuItemSelected(item);
    }

    @Override
    public void created() {
        Log.d(TAG, "created: ");
        ButterKnife.bind(this, mRootView);
        RxBus.get().register(this);
        ((AppCompatActivity) getContext()).setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                (Activity) getContext(), mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set default fragment as PunchFragment
        RxBus.get().post(BusAction.SET_FRAGMENT_IN_MAIN, PunchFragment.TAG);
    }

    @Override
    public void destroy() {
        Log.d(TAG, "destroy: ");
        RxBus.get().unregister(this);
    }

    @Override
    public void start() {
        Log.d(TAG, "start: ");
//        RxBus.get().register(this);
    }

    @Override
    public void stop() {
        Log.d(TAG, "stop: ");
//        RxBus.get().unregister(this);
    }

    @Override
    public void bindEvent() {
        mFab.setOnClickListener(view -> {

        });
        mNavigationView.setNavigationItemSelectedListener(MainView.this::onNavigationItemSelected);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_punch) {
            RxBus.get().post(BusAction.SET_FRAGMENT_IN_MAIN, PunchFragment.TAG);
        } else if (id == R.id.nav_diary) {
            RxBus.get().post(BusAction.SET_FRAGMENT_IN_MAIN, DiaryFragment.TAG);
        } else if (id == R.id.nav_memo) {
            RxBus.get().post(BusAction.SET_FRAGMENT_IN_MAIN, MemoFragment.TAG);
        } else if (id == R.id.nav_bidday) {
            RxBus.get().post(BusAction.SET_FRAGMENT_IN_MAIN, BigdayFragment.TAG);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Produce(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag(BusAction.SET_TAB_LAYOUT)}
    )
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
