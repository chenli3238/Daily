package com.wqy.daily.presenter;

import android.os.Bundle;
import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.BaseFragment;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.PunchEvents;
import com.wqy.daily.event.PunchInitEvent;
import com.wqy.daily.model.Event;
import com.wqy.daily.model.Pager;
import com.wqy.daily.model.Punch;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.PunchView;

import java.util.ArrayList;
import java.util.List;

public class PunchFragment extends BaseFragment {

    private static int PAGE_SIZE = 10;

    Pager mUnderwayPager;

    Pager mFinishedPager;

    Pager mDeletedPager;

    public static final String TAG = PunchFragment.class.getSimpleName();

    public PunchFragment() {
    }

    @Override
    public IView getIView() {
        return new PunchView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        Log.d(TAG, "created: ");
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        Log.d(TAG, "destroy: ");
        RxBus.get().unregister(this);
    }

    private Event createEvent() {
        Event event = new Event();
        event.setTitle("Event Title");
        event.setScore((float) (Math.random() * 100));
        return event;
    }

    private List<Event> createEvent(int n) {
        List<Event> events = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            events.add(createEvent());
        }
        return events;
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.INIT_PUNCH_UNDERWAY)})
    public void getPunchUnderway(PunchInitEvent event) {
        Log.d(TAG, "getPunchUnderway: ");
        PunchEvents events = new PunchEvents(createEvent(10));
        RxBus.get().post(BusAction.SET_PUNCH_UNDERWAY, events);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.INIT_PUNCH_FINISHED)})
    public void getPunchFinished(PunchInitEvent event) {
        Log.d(TAG, "getPunchFinished: ");
        PunchEvents events = new PunchEvents(createEvent(10));
        RxBus.get().post(BusAction.SET_PUNCH_FINISHED, events);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.INIT_PUNCH_DELETED)})
    public void getPunchDeleted(PunchInitEvent event) {
        Log.d(TAG, "getPunchDeleted: ");
        PunchEvents events = new PunchEvents(createEvent(10));
        RxBus.get().post(BusAction.SET_PUNCH_DELETED, events);
    }
}
