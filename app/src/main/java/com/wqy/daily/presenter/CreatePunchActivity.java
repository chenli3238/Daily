package com.wqy.daily.presenter;

import android.os.Bundle;
import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.BaseActivity;
import com.wqy.daily.CommonUtils;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.NumberPickerEvent;
import com.wqy.daily.model.DayTime;
import com.wqy.daily.model.Event;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.CreatePunchView;

public class CreatePunchActivity extends BaseActivity {

    private Event mEvent;

    @Override
    public IView getIView() {
        return new CreatePunchView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        RxBus.get().register(this);
        mEvent = new Event();
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_CPUNCH_TITLE)})
    public void setActivityTitle(String title) {
        setTitle(title);
    }

    @Subscribe(tags = {@Tag(BusAction.PUNCH_TITLE_DESC)})
    public void setTitleDesc(String[] event) {
        Log.d(TAG, "setTitleDesc: ");
        mEvent.setTitle(event[0]);
        mEvent.setDesc(event[1]);
    }

    @Subscribe(tags = {@Tag(BusAction.PUNCH_AIM)})
    public void setPunchAim(NumberPickerEvent event) {
        Log.d(TAG, "setPunchAim: " + event.getNumber());
        if (event.isMaxValue()) {
            mEvent.setEndless();
        } else {
            mEvent.setAim(event.getNumber());
        }
    }

    @Subscribe(tags = {@Tag(BusAction.DAY_TIME_PICKER_RESULT)})
    public void setPunchTime(DayTime event) {
        Log.d(TAG, "setPunchTime: " + CommonUtils.getDayTimeString(getResources(), event));
        mEvent.setPunchTime(event);
    }

    @Subscribe(tags = {@Tag(BusAction.PUNCH_DURATION)})
    public void setPunchDuration(Boolean b) {
        Log.d(TAG, "setPunchDuration: " + b);
        mEvent.setKeepDuration(b);
    }

    @Subscribe(tags = {@Tag(BusAction.PUNCH_REMIND)})
    public void setPunchRemind(Boolean remind) {
        Log.d(TAG, "setPunchRemind: " + remind);
        mEvent.setRemindme(remind);
    }

    @Subscribe(tags = {@Tag(BusAction.PUNCH_PRIORITY)})
    public void setPunchPriority(Integer priority) {
        Log.d(TAG, "setPunchPriority: " + priority);
        switch (priority) {
            case 0: mEvent.setPriority(Event.Priority.HIGH);
                break;
            case 1: mEvent.setPriority(Event.Priority.MEDIUM);
                break;
            case 2: mEvent.setPriority(Event.Priority.LOW);
                break;
            default:
                break;
        }
    }

    @Subscribe(tags = {@Tag(BusAction.CREATE_PUNCH)})
    public void createPunch(String s) {
        Log.d(TAG, "createPunch: ");
        // TODO: 17-2-9 register reminder

        // TODO: 17-2-9 save event
    }
}
