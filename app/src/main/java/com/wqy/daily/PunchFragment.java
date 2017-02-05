package com.wqy.daily;

import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.PunchView;

public class PunchFragment extends BaseFragment {

    public static final String TAG = PunchFragment.class.getSimpleName();

    public PunchFragment() {
    }

    @Override
    public IView getIView() {
        return new PunchView();
    }
}
