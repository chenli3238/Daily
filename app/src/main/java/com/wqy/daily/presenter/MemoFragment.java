package com.wqy.daily.presenter;

import com.wqy.daily.BaseFragment;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.MemoView;

public class MemoFragment extends BaseFragment {

    public static final String TAG = MemoFragment.class.getSimpleName();

    public MemoFragment() {}

    @Override
    public IView getIView() {
        return new MemoView();
    }
}
