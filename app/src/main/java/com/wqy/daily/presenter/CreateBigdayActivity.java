package com.wqy.daily.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;

import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.App;
import com.wqy.daily.BaseActivity;
import com.wqy.daily.CommonUtils;
import com.wqy.daily.NavigationUtils;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.DatasetChangedEvent;
import com.wqy.daily.event.ShowDialogEvent;
import com.wqy.daily.model.Bigday;
import com.wqy.daily.model.DaoMaster;
import com.wqy.daily.model.DaoSession;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.CreateBigdayView;

import java.util.Calendar;
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
        Log.d(TAG, "saveBigday: ");
        DatasetChangedEvent<Long> event = new DatasetChangedEvent<>();
        Long key = bigday.getId();
        if (key == null) {
            event.setAction(DatasetChangedEvent.INSERT);
        } else {
            event.setAction(DatasetChangedEvent.UPDATE);
        }
        event.setKeys(new Long[]{key});
        mDaoSession.getBigdayDao().save(bigday);
        setReminder(bigday);
        RxBus.get().post(BusAction.BIGDAY_DATASET_CHANGED, event);
    }

    private void setReminder(Bigday bigday) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = NavigationUtils.viewBigday(CreateBigdayActivity.this, bigday);
        PendingIntent pi = PendingIntent.getActivity(CreateBigdayActivity.this, 0, intent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, bigday.getDate().getTime(), pi);
    }
}
