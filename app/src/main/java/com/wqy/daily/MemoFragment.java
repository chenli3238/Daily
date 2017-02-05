package com.wqy.daily;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
