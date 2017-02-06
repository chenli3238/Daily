package com.wqy.daily.presenter;

import com.wqy.daily.BaseFragment;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.BigdayView;

public class BigdayFragment extends BaseFragment {

    public static final String TAG = BigdayFragment.class.getSimpleName();

    public BigdayFragment() {
    }

    @Override
    public IView getIView() {
        return new BigdayView();
    }
}
