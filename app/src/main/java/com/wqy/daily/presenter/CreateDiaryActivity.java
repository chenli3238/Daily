package com.wqy.daily.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.BaseActivity;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.model.Diary;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.CreateDiaryView;

import java.util.Calendar;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean b =  super.onCreateOptionsMenu(menu);
        RxBus.get().post(BusAction.CDIARY_EDITABLE, Boolean.TRUE);
        return b;
    }

    @Subscribe(tags = {@Tag(BusAction.SET_CDIARY_TITLE)})
    public void setActivityTitle(String title) {
        setTitle(title);
    }

    @Subscribe(tags = {@Tag(BusAction.CREATE_DIARY)})
    public void createDiary(String text) {
        Calendar today = Calendar.getInstance();
        Diary diary = new Diary();
        diary.setText(text);
        diary.setDate(today.getTime());
        // TODO: 17-2-10 handle diary pictures
    }
}
