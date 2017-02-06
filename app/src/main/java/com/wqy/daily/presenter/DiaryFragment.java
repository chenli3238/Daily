package com.wqy.daily.presenter;

import com.wqy.daily.BaseFragment;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.DiaryView;

/**
 * Created by wqy on 17-2-5.
 */

public class DiaryFragment extends BaseFragment {

    public static final String TAG = DiaryFragment.class.getSimpleName();

    public DiaryFragment() {}

    @Override
    public IView getIView() {
        return new DiaryView();
    }
}
