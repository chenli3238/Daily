package com.wqy.daily.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.mvp.ViewImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-7.
 */

public class CreatePunchView extends ViewImpl {

    @BindView(R.id.cpunch_toolbar)
    Toolbar mToolbar;

    @Override
    public int getResId() {
        return R.layout.activity_create_punch;
    }

    @Override
    public void created() {
        ButterKnife.bind(this, mRootView);
        ((AppCompatActivity) getContext()).setSupportActionBar(mToolbar);
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Produce(tags = {@Tag(BusAction.SET_CPUNCH_TITLE)})
    public String setActivityTitle() {
        return getContext().getString(R.string.cpunch_title);
    }
}
