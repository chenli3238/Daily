package com.wqy.daily.view;

import android.content.Context;
import android.support.design.widget.TabLayout;

import com.wqy.daily.R;
import com.wqy.daily.mvp.ViewImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-4.
 */

public class PunchView extends ViewImpl {

    @BindView(R.id.punch_tab_layout)
    TabLayout mTabLayout;

    @Override
    public Context getContext() {
        return mRootView.getContext();
    }

    @Override
    public int getResId() {
        return R.layout.fragment_punch;
    }

    @Override
    public void created() {
        ButterKnife.bind(this, mRootView);
    }

    @Override
    public void bindEvent() {

    }
}
