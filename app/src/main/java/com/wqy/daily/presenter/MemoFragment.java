package com.wqy.daily.presenter;

import android.os.Bundle;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.BaseFragment;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.MemoEvents;
import com.wqy.daily.event.MemoInitEvent;
import com.wqy.daily.model.Memo;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.MemoView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoFragment extends BaseFragment {

    public static final String TAG = MemoFragment.class.getSimpleName();

    public MemoFragment() {}

    @Override
    public IView getIView() {
        return new MemoView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    private Memo createMemo() {
        Memo memo = new Memo();
        memo.setTitle("备忘");
        memo.setContent("如果你无法简洁的表达你的想法，那只说明你还不够了解它。\n" +
                "-- 阿尔伯特·爱因斯坦");
        memo.setDate(new Date());
        return memo;
    }

    private List<Memo> createMemo(int n) {
        List<Memo> memos = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            memos.add(createMemo());
        }
        return memos;
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.INIT_MEMO_UNDERWAY)})
    public void getMemoUnderway(MemoInitEvent event) {
        MemoEvents events = new MemoEvents(createMemo(10));
        RxBus.get().post(BusAction.SET_MEMO_UNDERWAY, events);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.INIT_MEMO_FINISHED)})
    public void getMemoFinished(MemoInitEvent event) {
        MemoEvents events = new MemoEvents(createMemo(10));
        RxBus.get().post(BusAction.SET_MEMO_FINISHED, events);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.INIT_MEMO_DELETED)})
    public void getMemoDeleted(MemoInitEvent event) {
        MemoEvents events = new MemoEvents(createMemo(10));
        RxBus.get().post(BusAction.SET_MEMO_DELETED, events);
    }
}
