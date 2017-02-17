package com.wqy.daily;

import com.wqy.daily.mvp.FragmentPresenterImpl;
import com.wqy.daily.mvp.IView;

/**
 * Created by wqy on 17-2-4.
 */

public abstract class BaseFragment extends FragmentPresenterImpl {
    @Override
    public IView getIView() {
        return mView;
    }
}
