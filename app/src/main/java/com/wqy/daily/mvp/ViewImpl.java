package com.wqy.daily.mvp;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wqy on 17-2-4.
 */

public abstract class ViewImpl implements IActivityView {

    protected IPresenter mIPresenter;

    protected View mRootView;

    @Override
    public View create(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(getResId(), container, false);
        mRootView = view;
        return view;
    }

    @Override
    public void bindPresenter(IPresenter presenter) {
        mIPresenter = presenter;
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        return false;
    }

    public Context getContext() {
        return mRootView.getContext();
    }

    public void setSupportActionBar(Toolbar toolbar) {
        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);
    }

    public ActionBar getSupportActionBar() {
        return ((AppCompatActivity) getContext()).getSupportActionBar();
    }

    @Override
    public void created() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public int getMenuId() {
        return 0;
    }

    @Override
    public int getFragmentContainerId() {
        return 0;
    }
}
