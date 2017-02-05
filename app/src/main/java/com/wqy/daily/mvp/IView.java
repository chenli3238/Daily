package com.wqy.daily.mvp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wqy on 17-2-4.
 */

public interface IView {

    // invoke before setContentView
    // create the root View
    View create(LayoutInflater inflater, ViewGroup container);

    // invoke after presenter created
    // bind data to views
    void created();

    // onDestroy
    void destroy();

    // onStart
    void start();

    // onStop
    void stop();

    // return the root layout id
    int getResId();

    void bindPresenter(IPresenter presenter);

    // bind view events
    void bindEvent();
}
