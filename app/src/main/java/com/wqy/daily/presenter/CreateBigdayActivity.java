package com.wqy.daily.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.BaseActivity;
import com.wqy.daily.CommonUtils;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.ShowDialogEvent;
import com.wqy.daily.model.Bigday;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.CreateBigdayView;

import java.util.Calendar;

public class CreateBigdayActivity extends BaseActivity {

    private Bigday mBigday;

    @Override
    public IView getIView() {
        return new CreateBigdayView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        mBigday = new Bigday();
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

    @Subscribe(tags = {@Tag(BusAction.SHOW_DIALOG)})
    public void showDialog(ShowDialogEvent event) {
        event.getFragment().show(getSupportFragmentManager(), event.getTag());
    }

    @Subscribe(tags = {@Tag(BusAction.BIGDAY_TITLE_DESC)})
    public void setTitleDesc(String[] event) {
        Log.d(TAG, "setTitleDesc: ");
        mBigday.setTitle(event[0]);
        mBigday.setDesc(event[1]);
    }

    @Subscribe(tags = {@Tag(BusAction.DATE_TIME_PICKER_RESULLT)})
    public void setBigdayTime(Calendar event) {
        Log.d(TAG, "setBigdayTime: ");
        mBigday.setDate(event.getTime());
    }

    @Subscribe(tags = {@Tag(BusAction.TAG_PICKER_RESULT)})
    public void setBigdayTags(String[] tags) {
        Log.d(TAG, "setBigdayTags: " + CommonUtils.getTagString(getResources(), tags));
//        mBigday.setTags(tags);
    }

    @Subscribe(tags = {@Tag(BusAction.CREATE_BIGDAY)})
    public void createBigday(String s) {
        Log.d(TAG, "createBigday: ");
    }
}
