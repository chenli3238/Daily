package com.wqy.daily.mvp;

import android.os.Bundle;

import com.wqy.daily.mvp.IView;

/**
 * Created by wqy on 17-2-4.
 */

public interface IPresenter {

    void create(Bundle savedInstanceState);

    void created(Bundle savedInstanceState);

    IView getIView();
}
