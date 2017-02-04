package com.wqy.daily;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.MainView;

public class MainActivity extends BaseActivity {

    Fragment mPunchFragment;

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
        mPunchFragment = new PunchFragment();
        if (mActivityView != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(mActivityView.getFragmentContainerId(), mPunchFragment, PunchFragment.TAG)
                    .show(mPunchFragment)
                    .commit();
        }
    }
}
