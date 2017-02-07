package com.wqy.daily.presenter;

import android.os.Bundle;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.BaseFragment;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.DiaryEvent;
import com.wqy.daily.model.Diary;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.DiaryView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wqy on 17-2-5.
 */

public class DiaryFragment extends BaseFragment {

    public static final String TAG = DiaryFragment.class.getSimpleName();

    public DiaryFragment() {}

    @Override
    public IView getIView() {
        return new DiaryView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    private Diary createDiary() {
        Diary diary = new Diary();
        diary.setText("如果你无法简洁的表达你的想法，那只说明你还不够了解它。\n" +
                "-- 阿尔伯特·爱因斯坦");
        Calendar calendar = Calendar.getInstance();
        int offset = - (int) (Math.random() * 365);

        calendar.add(Calendar.DAY_OF_YEAR, offset);
        diary.setDate(calendar.getTime());
        return diary;
    }

    private List<Diary> createDiary(int n) {
        List<Diary> diaries = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            diaries.add(createDiary());
        }
        return diaries;
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.INIT_DIARY)})
    public void initDiary(String s) {
        List<Diary> diaries = createDiary(10);
        RxBus.get().post(BusAction.SET_DIARY, new DiaryEvent(diaries));
    }
}
