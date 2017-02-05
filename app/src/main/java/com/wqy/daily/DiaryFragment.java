package com.wqy.daily;

import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.DiaryView;

/**
 * Created by wqy on 17-2-5.
 */

public class DiaryFragment extends BaseFragment {

    public static final String TAG = "DiaryFragment";

    public DiaryFragment() {}

    @Override
    public String getTitle() {
        return getString(R.string.title_diary);
    }

    @Override
    public IView getIView() {
        return new DiaryView();
    }
}
