package com.wqy.daily;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.BigdayView;

public class BigdayFragment extends BaseFragment {

    public BigdayFragment() {
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_bigday);
    }

    @Override
    public IView getIView() {
        return new BigdayView();
    }
}
