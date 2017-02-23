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
import com.wqy.daily.event.DataEvent;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.DatasetChangedEvent;
import com.wqy.daily.model.Bigday;
import com.wqy.daily.model.BigdayDao;
import com.wqy.daily.model.DaoSession;
import com.wqy.daily.model.Pager;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.BigdayView;

import java.util.Date;
import java.util.List;

public class BigdayFragment extends BaseFragment {

    public static final int PAGE_SIZE = 10;

    private DaoSession mDaoSession;
    private Pager mBackwardPager;
    private Pager mForwardPager;


    public static final String TAG = BigdayFragment.class.getSimpleName();

    public BigdayFragment() {
    }

    @Override
    public IView createIView() {
        return new BigdayView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        mDaoSession = ((App) getActivity().getApplicationContext()).getDaoSession();
        mBackwardPager = new Pager(PAGE_SIZE);
        mForwardPager = new Pager(PAGE_SIZE);
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.LOAD_BIGDAY_BACKWARD)})
    public void getBigdayBackward(DataEvent<Bigday> event) {
        Log.d(TAG, "getBigdayBackward: " + event.getAction());
        switch (event.getAction()) {
            case DataEvent.LOAD_MORE:
                break;
            case DataEvent.REFRESH:
                mBackwardPager.reset();
                break;
        }
        Log.d(TAG,  String.format("limit = %d, offset = %d", mBackwardPager.getLimit(), mForwardPager.getOffset()));
//        Date today = CommonUtils.getTodayBegin().getTime();
        Date today = new Date();
        List<Bigday> bigdays = mDaoSession.getBigdayDao().queryBuilder()
                .where(BigdayDao.Properties.Date.gt(today))
//                .where(BigdayDao.Properties.Id.gt(mBackwardPager.getLastId()))
                .orderAsc(BigdayDao.Properties.Date)
                .limit(mBackwardPager.getLimit())
                .offset(mBackwardPager.getOffset())
                .list();
        if (bigdays == null || bigdays.size() == 0) {
            event.setHasMore(false);
        } else  {
            // TODO: 17-2-11 Fix logical problem
//            mBackwardPager.setLastId(bigdays.get(bigdays.size() - 1).getId());
            mBackwardPager.addOffset(bigdays.size());
        }

        event.setDatas(bigdays);
        RxBus.get().post(BusAction.SET_BIGDAY_BACKWARD, event);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.LOAD_BIGDAY_FORWARD)})
    public void getBigdayForward(DataEvent<Bigday> event) {
        Log.d(TAG, "getBigdayForward: " + event.getAction());
        switch (event.getAction()) {
            case DataEvent.LOAD_MORE:
                break;
            case DataEvent.REFRESH:
                mForwardPager.reset();
                break;
        }
        Log.d(TAG,  String.format("limit = %d, offset = %d", mForwardPager.getLimit(), mForwardPager.getOffset()));
//        Date today = CommonUtils.getTodayBegin().getTime();
        Date today = new Date();
        List<Bigday> bigdays = mDaoSession.getBigdayDao().queryBuilder()
                .where(BigdayDao.Properties.Date.lt(today))
//                .where(BigdayDao.Properties.Id.gt(mForwardPager.getLastId()))
                .orderAsc(BigdayDao.Properties.Date)
                .limit(mForwardPager.getLimit())
                .offset(mForwardPager.getOffset())
                .list();
        if (bigdays == null || bigdays.size() == 0) {
            event.setHasMore(false);
        } else  {
            mForwardPager.setLastId(bigdays.get(bigdays.size() - 1).getId());
            mForwardPager.addOffset(bigdays.size());
        }
        event.setDatas(bigdays);
        RxBus.get().post(BusAction.SET_BIGDAY_FORWARD, event);
    }

    @Subscribe(tags = {@Tag(BusAction.CREATE_BIGDAY)})
    public void createBigday(String s) {
        Intent intent = NavigationUtils.editBigday(getContext());
        startActivity(intent);
    }

    @Subscribe(tags = {@Tag(BusAction.VIEW_BIGDAY)})
    public void viewBigday(Bigday bigday) {
        Log.d(TAG, "viewBigday: ");
        Intent intent = NavigationUtils.viewBigday(getContext(), bigday);
        startActivity(intent);
    }

    @Subscribe(tags = {@Tag(BusAction.DELETE_ALL)})
    public void deleteAll(String s) {
        mDaoSession.getBigdayDao().deleteAll();
        DatasetChangedEvent event = new DatasetChangedEvent(DatasetChangedEvent.DELETE);
        RxBus.get().post(BusAction.BIGDAY_DATASET_CHANGED, event);
    }
}
