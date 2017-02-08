package com.wqy.daily.mvp;

import android.view.MenuItem;

import com.wqy.daily.mvp.IView;

/**
 * Created by wqy on 17-2-4.
 */

public interface IMenuView extends IView {

    int getMenuId();

    boolean onMenuItemSelected(MenuItem item);
}
