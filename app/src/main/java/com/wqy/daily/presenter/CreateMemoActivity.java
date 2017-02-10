package com.wqy.daily.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.BaseActivity;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.model.Memo;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.CreateMemoView;

import java.util.Calendar;

public class CreateMemoActivity extends BaseActivity {

    private Memo mMemo;

    @Override
    public IView getIView() {
        return new CreateMemoView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        RxBus.get().register(this);
        mMemo = new Memo();
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_CMEMO_TITLE)})
    public void setActivityTitle(String title) {
        setTitle(title);
    }

    @Subscribe(tags = {@Tag(BusAction.DATE_TIME_PICKER_RESULLT)})
    public void setRemind(Calendar event) {
        Log.d(TAG, "setRemind: ");
        mMemo.setDate(event.getTime());
    }

    @Subscribe(tags = {@Tag(BusAction.CREATE_MEMO)})
    public void createMemo(String content) {
        Log.d(TAG, "createMemo: " + content);
        mMemo.setContent(content);
        // TODO: 17-2-10 save memo
    }
}
