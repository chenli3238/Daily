package com.wqy.daily.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by wqy on 17-2-4.
 */

public abstract class ActivityPresenterImpl extends AppCompatActivity implements IPresenter {

    public static final String TAG = ActivityPresenterImpl.class.getSimpleName();

    protected IView mView;
    protected IActivityView mActivityView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");

        // do some configuration
        create(savedInstanceState);

        // create an IView instance
        mView = getIView();
        mActivityView = getActivityView();
        mView.bindPresenter(this);

        // create and set content view
        View contentView = mView.create(getLayoutInflater(), null);
        setContentView(contentView);

        // do some loading work
        created(savedInstanceState);

        // bind data to views
        mView.created();

        // bind events
        mView.bindEvent();

        // do some optional work
        prepared(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: ");
        mView.destroy();
        destroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");
        start();
        mView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop: ");
        mView.stop();
        stop();
    }

    public IActivityView getActivityView() {
        try {
            mActivityView = IActivityView.class.cast(mView);
        } catch (ClassCastException e) {
            mActivityView = null;
        }
        return mActivityView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        if (mActivityView == null) return false;
        int menuId = mActivityView.getMenuId();
        if (menuId <= 0) return false;
        getMenuInflater().inflate(menuId, menu);
        mActivityView.setMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mActivityView != null && mActivityView.onMenuItemSelected(item);
    }

    @Override
    public void create(Bundle savedInstanceState) {

    }

    @Override
    public void created(Bundle savedInstanceState) {

    }

    @Override
    public void prepared(Bundle savedInstanceState) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }
}
