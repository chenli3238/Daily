package com.wqy.daily.mvp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wqy on 17-2-4.
 */

public interface IView {

    View create(LayoutInflater inflater, ViewGroup container);

    void created();

    void destroy();

    void start();

    void stop();

    int getResId();

    void bindPresenter(IPresenter presenter);

    void bindEvent();
}
