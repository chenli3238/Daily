package com.wqy.daily.presenter;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.App;
import com.wqy.daily.BaseActivity;
import com.wqy.daily.NavigationUtils;
import com.wqy.daily.R;
import com.wqy.daily.ReminderUtils;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.DatasetChangedEvent;
import com.wqy.daily.model.Bigday;
import com.wqy.daily.model.DaoSession;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.CreateBigdayView;

import java.util.Date;

public class CreateBigdayActivity extends BaseActivity {
    public static final String TAG = CreateBigdayActivity.class.getSimpleName();

    public static final String EXTRA_BIGDAY_ID = "bigdayId";

    DaoSession mDaoSession;

    @Override
    public IView createIView() {
        return new CreateBigdayView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        mDaoSession = ((App) getApplication()).getDaoSession();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        RxBus.get().register(this);
    }

    @Override
    public void prepared(Bundle savedInstanceState) {
        String action = getIntent().getAction();
        Bigday bigday;
        if (action == null || action.equals(getString(R.string.action_edit))) {
            RxBus.get().post(BusAction.CBIGDAY_EDITABLE, Boolean.TRUE);
            bigday = new Bigday();
            bigday.setDate(new Date());
        } else {
            RxBus.get().post(BusAction.CBIGDAY_EDITABLE, Boolean.FALSE);
            long id = getIntent().getLongExtra(EXTRA_BIGDAY_ID, 0);
            bigday = mDaoSession.getBigdayDao().load(id);
        }
        RxBus.get().post(BusAction.CBIGDAY_SET_BIGDAY, bigday);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_CBIGDAY_TITLE)})
    public void setActivityTitle(String title) {
        setTitle(title);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.SAVE_BIGDAY)})
    public void saveBigday(Bigday bigday) {
        Log.d(TAG, "saveBigday: " + bigday.getId());
        DatasetChangedEvent event = new DatasetChangedEvent();
        Long key = bigday.getId();
        if (key == null) {
            event.setAction(DatasetChangedEvent.INSERT);
        } else {
            event.setAction(DatasetChangedEvent.UPDATE);
        }

        mDaoSession.getBigdayDao().save(bigday);
        setReminder(bigday);
        RxBus.get().post(BusAction.BIGDAY_DATASET_CHANGED, event);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.DELETE_BIGDAY)})
    public void deleteBigday(Bigday bigday) {
        Log.d(TAG, "deleteBigday: ");
        removeReminder(bigday);
        mDaoSession.getBigdayDao().delete(bigday);
        DatasetChangedEvent event = new DatasetChangedEvent(DatasetChangedEvent.DELETE);
        RxBus.get().post(BusAction.BIGDAY_DATASET_CHANGED, event);
    }

    private void setReminder(Bigday bigday) {
        ReminderUtils.scheduleNotification(this, getNotification(bigday),
                getNotificationId(bigday), bigday.getDate().getTime());
    }

    private void removeReminder(Bigday bigday) {
        ReminderUtils.cancelNotification(this, getNotification(bigday),
                getNotificationId(bigday));
    }

    private Notification getNotification(Bigday bigday) {
        Intent intent = NavigationUtils.viewBigday(CreateBigdayActivity.this, bigday);
        PendingIntent pi = PendingIntent.getActivity(CreateBigdayActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentIntent(pi)
                .setContentTitle(bigday.getTitle())
                .setContentText(getString(R.string.reminder_info))
//                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }

    private int getNotificationId(Bigday bigday) {
        int id = (int) (bigday.getDate().getTime() * 7 + bigday.getId() * 31);
        return id;
    }
}
