package com.wqy.daily.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by wqy on 17-2-4.
 */

public abstract class ActivityPresenterImpl extends AppCompatActivity implements IPresenter {

    protected IView mView;
    private IMenuView mIMenuView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(savedInstanceState);

        // create an IView instance
        mView = getView();
        mView.bindPresenter(this);

        // create and set content view
        setContentView(mView.create(getLayoutInflater(), null));

        // bind views
        mView.created();

        // bind events
        mView.bindEvent();

        created(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            mIMenuView = IMenuView.class.cast(mView);
            int menuId = mIMenuView.getMenuId();
            if (menuId <= 0) return false;
            getMenuInflater().inflate(menuId, menu);
            return true;
        } catch (ClassCastException e) {
            mIMenuView = null;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mIMenuView != null && mIMenuView.onMenuItemSelected(item);
    }

    @Override
    public void create(Bundle savedInstanceState) {

    }

    @Override
    public void created(Bundle savedInstanceState) {

    }
}
