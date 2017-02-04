package com.wqy.daily.mvp;

import android.view.MenuItem;

/**
 * Created by wqy on 17-2-4.
 */

public interface IActivityView extends IView {

    int getMenuId();

    boolean onMenuItemSelected(MenuItem item);

    /**
     * @return id of the container view for the Fragment
     */
    int getFragmentContainerId();
}
