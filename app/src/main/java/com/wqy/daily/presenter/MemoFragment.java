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
import com.wqy.daily.model.DaoSession;
import com.wqy.daily.model.Memo;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.MemoView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoFragment extends BaseFragment {

    public static final String TAG = MemoFragment.class.getSimpleName();
    DaoSession mDaoSession;

    public MemoFragment() {}

    @Override
    public IView createIView() {
        return new MemoView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        mDaoSession = ((App) getContext().getApplicationContext()).getDaoSession();
        RxBus.get().register(this);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.LOAD_MEMO_UNDERWAY)})
    public void getMemoUnderway(DataEvent<Memo> event) {
        Log.d(TAG, "getMemoUnderway: ");
        List<Memo> memos = mDaoSession.getMemoDao().loadAll();
        event.setDatas(memos);
        RxBus.get().post(BusAction.SET_MEMO_UNDERWAY, event);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.LOAD_MEMO_FINISHED)})
    public void getMemoFinished(DataEvent<Memo> event) {
        Log.d(TAG, "getMemoFinished: ");
        List<Memo> memos = mDaoSession.getMemoDao().loadAll();
        event.setDatas(memos);
        RxBus.get().post(BusAction.SET_MEMO_FINISHED, event);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.LOAD_MEMO_DELETED)})
    public void getMemoDeleted(DataEvent<Memo> event) {
        Log.d(TAG, "getMemoDeleted: ");
        List<Memo> memos = mDaoSession.getMemoDao().loadAll();
        event.setDatas(memos);
        RxBus.get().post(BusAction.SET_MEMO_DELETED, event);
    }

    @Subscribe(tags = {@Tag(BusAction.VIEW_MEMO)})
    public void viewMemo(Memo memo) {
        Intent intent = NavigationUtils.memo(getContext(), memo);
        startActivity(intent);
    }
}
