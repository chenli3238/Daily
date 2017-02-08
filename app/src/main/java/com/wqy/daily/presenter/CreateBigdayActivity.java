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
import com.wqy.daily.view.CreateBigdayView;

public class CreateBigdayActivity extends BaseActivity {

    @Override
    public IView getIView() {
        return new CreateBigdayView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_CBIGDAY_TITLE)})
    public void setActivityTitle(String title) {
        setTitle(title);
    }
}
