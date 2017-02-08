package com.wqy.daily.mvp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wqy.daily.mvp.IMenuView;
import com.wqy.daily.mvp.IPresenter;

/**
 * Created by wqy on 17-2-4.
 */

public abstract class ViewImpl implements IMenuView {

    protected IPresenter mIPresenter;

    protected View mRootView;

    @Override
    public View create(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(getResId(), container);
        mRootView = view;
        return view;
    }

    @Override
    public void bindPresenter(IPresenter presenter) {
        mIPresenter = presenter;
    }

    @Override
    public int getMenuId() {
        return 0;
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        return false;
    }

    public abstract Context getContext();
}
