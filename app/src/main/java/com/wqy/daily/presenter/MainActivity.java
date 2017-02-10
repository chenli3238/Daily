package com.wqy.daily.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.BaseActivity;
import com.wqy.daily.BaseFragment;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.MainView;


public class MainActivity extends BaseActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private ActionBar mActionBar;

    @Override
    public void create(Bundle savedInstanceState) {
        Log.d(TAG, "create: ");
    }

    @Override
    public void created(Bundle savedInstanceState) {
        Log.d(TAG, "created: ");
        RxBus.get().register(this);
    }

    @Override
    public void prepared(Bundle savedInstanceState) {
//        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public IView getIView() {
        return new MainView();
    }

    private BaseFragment getFragmentByTag(String tag) {
        Class clazz = null;
        if (PunchFragment.TAG.equals(tag)) {
            clazz = PunchFragment.class;
        } else if (DiaryFragment.TAG.equals(tag)) {
            clazz = DiaryFragment.class;
        } else if (MemoFragment.TAG.equals(tag)) {
            clazz = MemoFragment.class;
        } else if (BigdayFragment.TAG.equals(tag)) {
            clazz = BigdayFragment.class;
        }

        if (clazz == null) {
            // default fragment
            clazz = PunchFragment.class;
        }

        BaseFragment fragment = null;
        try {
            fragment = (BaseFragment) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Subscribe(tags = {@Tag(BusAction.SET_FRAGMENT_IN_MAIN)})
    public void setFragmentByTag(String tag) {
        Log.d(TAG, "setFragmentByTag: " + tag);
        Fragment fragment = getFragmentByTag(tag);
        getSupportFragmentManager().beginTransaction()
                .replace(mActivityView.getFragmentContainerId(),
                        fragment, tag)
                .commit();
    }

    @Subscribe(tags = {@Tag(BusAction.SET_MAIN_ACTIVITY_TITLE)})
    public void setActivityTitle(String title) {
        setTitle(title);
    }

    @Subscribe(tags = {@Tag(BusAction.START_ACTIVITY)})
    public void startActivity(Class<?> clazz) {
        Intent intent = new Intent(MainActivity.this, clazz);
        startActivity(intent);
    }


}
