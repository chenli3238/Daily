package com.wqy.daily.presenter;

import android.os.Bundle;
import android.view.Menu;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.BaseActivity;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.CreatePunchView;

public class CreatePunchActivity extends BaseActivity {


    @Override
    public IView getIView() {
        return new CreatePunchView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_CPUNCH_TITLE)})
    public void setActivityTitle(String title) {
        setTitle(title);
    }
}
