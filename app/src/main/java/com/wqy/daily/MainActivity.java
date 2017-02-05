package com.wqy.daily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.MainView;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;


public class MainActivity extends BaseActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public void created(Bundle savedInstanceState) {
        Log.d(TAG, "created: ");
    }

    private BaseFragment getFragmentByTag(String tag) {
        Class clazz = null;
        if (PunchFragment.TAG.equals(tag)) {
            clazz = PunchFragment.class;
        } else if (MemoFragment.TAG.equals(tag)) {
            clazz = MemoFragment.class;
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

    @Produce(
            tags = {@Tag("test")}
    )
    public String getTest() {
        return "test string";
    }

    @Subscribe(
            tags = {@Tag("test")}
    )
    public void logString(String test) {
        Log.d(TAG, "logString: " + test);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag(BusAction.SET_FRAGMENT_IN_MAIN)}
    )
    public void setFragmentByTag(String tag) {
        Log.d(TAG, "setFragmentByTag: " + tag);
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (getSupportFragmentManager().getFragments() != null) {
            // hide all fragments
            Observable.from(getSupportFragmentManager().getFragments())
                    .subscribe(transaction::hide);
        }

        if (fragment == null) {
            fragment = getFragmentByTag(tag);
            transaction.add(mActivityView.getFragmentContainerId(), fragment, tag);
        }

        transaction.show(fragment);
        transaction.commit();
    }
}
