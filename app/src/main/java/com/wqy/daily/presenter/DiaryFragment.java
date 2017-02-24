package com.wqy.daily.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.App;
import com.wqy.daily.BaseFragment;
import com.wqy.daily.NavigationUtils;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.DataEvent;
import com.wqy.daily.event.DiaryEvent;
import com.wqy.daily.model.DaoSession;
import com.wqy.daily.model.Diary;
import com.wqy.daily.model.DiaryDao;
import com.wqy.daily.model.Pager;
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

    private DaoSession mDaoSession;
    private Pager mPager;

    public DiaryFragment() {}

    @Override
    public IView createIView() {
        return new DiaryView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        mDaoSession = ((App) getActivity().getApplicationContext()).getDaoSession();
        mPager = new Pager();
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
            tags = {@Tag(BusAction.LOAD_DIARY)})
    public void getDiary(DataEvent<Diary> event) {
        Log.d(TAG, "getDiary: ");
        if (event.getAction() == DataEvent.REFRESH) {
            mPager.reset();
        }
        List<Diary> diaries = mDaoSession.getDiaryDao().queryBuilder()
                .orderDesc(DiaryDao.Properties.Date)
                .limit(mPager.getLimit())
                .offset(mPager.getOffset())
                .list();
        if (diaries == null || diaries.size() == 0) {
            event.setHasMore(false);
        } else {
            mPager.addOffset(diaries.size());
        }
        event.setDatas(diaries);

        RxBus.get().post(BusAction.SET_DIARY, event);
    }

    @Subscribe(tags = {@Tag(BusAction.CREATE_DIARY)})
    public void createDiary(String s) {
        Intent intent = NavigationUtils.createDiary(getActivity());
        startActivity(intent);
    }

    @Subscribe(tags = {@Tag(BusAction.VIEW_DIARY)})
    public void viewDiary(Diary diary) {
        Intent intent = NavigationUtils.viewDiary(getActivity(), diary);
        startActivity(intent);
    }
}
