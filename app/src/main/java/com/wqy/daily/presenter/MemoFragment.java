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
import com.wqy.daily.model.BigdayDao;
import com.wqy.daily.model.DaoSession;
import com.wqy.daily.model.Memo;
import com.wqy.daily.model.MemoDao;
import com.wqy.daily.model.Pager;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.MemoView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoFragment extends BaseFragment {

    public static final String TAG = MemoFragment.class.getSimpleName();

    public static final int PAGE_SIZE = 10;

    private DaoSession mDaoSession;
    private Pager mUnderwayPager;
    private Pager mFinishedPager;
    private Pager mDeletedPager;

    public MemoFragment() {}

    @Override
    public IView createIView() {
        return new MemoView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        mDaoSession = ((App) getContext().getApplicationContext()).getDaoSession();
        mUnderwayPager = new Pager(PAGE_SIZE);
        mFinishedPager = new Pager(PAGE_SIZE);
        mDeletedPager = new Pager(PAGE_SIZE);
        RxBus.get().register(this);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.LOAD_MEMO_UNDERWAY)})
    public void getMemoUnderway(DataEvent<Memo> event) {
        Log.d(TAG, "getMemoUnderway: ");
        if (event.getAction() == DataEvent.REFRESH) {
            mUnderwayPager.reset();
        }
        List<Memo> memos = mDaoSession.getMemoDao().queryBuilder()
                .where(MemoDao.Properties.Finished.eq(false),
                        MemoDao.Properties.Deleted.eq(false))
                .orderDesc(MemoDao.Properties.CreatedAt)
                .limit(mUnderwayPager.getLimit())
                .offset(mUnderwayPager.getOffset())
                .list();
        if (memos == null || memos.size() == 0) {
            event.setNoMore(true);
        } else {
            mUnderwayPager.addOffset(memos.size());
        }
        event.setDatas(memos);
        RxBus.get().post(BusAction.SET_MEMO_UNDERWAY, event);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.LOAD_MEMO_FINISHED)})
    public void getMemoFinished(DataEvent<Memo> event) {
        Log.d(TAG, "getMemoFinished: ");
        if (event.getAction() == DataEvent.REFRESH) {
            mFinishedPager.reset();
        }
        List<Memo> memos = mDaoSession.getMemoDao().queryBuilder()
                .where(MemoDao.Properties.Deleted.eq(false),
                        MemoDao.Properties.Finished.eq(true))
                .orderDesc(MemoDao.Properties.CreatedAt)
                .limit(mFinishedPager.getLimit())
                .offset(mFinishedPager.getOffset())
                .list();
        if (memos == null || memos.size() == 0) {
            mFinishedPager.addOffset(memos.size());
        }
        event.setDatas(memos);
        RxBus.get().post(BusAction.SET_MEMO_FINISHED, event);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.LOAD_MEMO_DELETED)})
    public void getMemoDeleted(DataEvent<Memo> event) {
        Log.d(TAG, "getMemoDeleted: ");
        List<Memo> memos = mDaoSession.getMemoDao().queryBuilder()
                .where(MemoDao.Properties.Deleted.eq(true))
                .orderDesc(MemoDao.Properties.CreatedAt)
                .limit(mDeletedPager.getLimit())
                .offset(mDeletedPager.getOffset())
                .list();
        if (memos == null || memos.size() == 0) {
            mDeletedPager.addOffset(memos.size());
        }
        event.setDatas(memos);
        RxBus.get().post(BusAction.SET_MEMO_DELETED, event);
    }

    @Subscribe(tags = {@Tag(BusAction.VIEW_MEMO)})
    public void viewMemo(Memo memo) {
        Intent intent = NavigationUtils.memo(getActivity(), memo);
        startActivity(intent);
    }
}
