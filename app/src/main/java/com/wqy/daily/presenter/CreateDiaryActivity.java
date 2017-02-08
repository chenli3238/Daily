package com.wqy.daily.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.BaseActivity;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.CreateDiaryView;

public class CreateDiaryActivity extends BaseActivity {


    @Override
    public IView getIView() {
        return new CreateDiaryView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_CDIARY_TITLE)})
    public void setActivityTitle(String title) {
        setTitle(title);
    }
}
