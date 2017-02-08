package com.wqy.daily.mvp;

import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by wqy on 17-2-4.
 */

public interface IActivityView extends IView {

    int getMenuId();

    Menu getMenu();

    void setMenu(Menu menu);

    boolean onMenuItemSelected(MenuItem item);

    /**
     * @return id of the container view for the Fragment
     */
    int getFragmentContainerId();
}
