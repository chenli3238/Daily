package com.wqy.daily.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.App;
import com.wqy.daily.BaseActivity;
import com.wqy.daily.CommonUtils;
import com.wqy.daily.NavigationUtils;
import com.wqy.daily.ReminderUtils;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.DatasetChangedEvent;
import com.wqy.daily.model.DaoSession;
import com.wqy.daily.model.Memo;
import com.wqy.daily.model.Reminder;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.CreateMemoView;

public class CreateMemoActivity extends BaseActivity {

    private static final int IMAGE_REQUEST_CODE = 1000;
    public static final String EXTRA_MEMO_ID = "EXTRA_MEMO_ID";

    DaoSession mDaoSession;

    @Override
    public IView createIView() {
        return new CreateMemoView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        mDaoSession = ((App) getApplication()).getDaoSession();
        RxBus.get().register(this);
    }

    @Override
    public void prepared(Bundle savedInstanceState) {
        setMemo();
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    private void setMemo() {
        Memo memo;
        long id = getIntent().getLongExtra(EXTRA_MEMO_ID, 0);
        if (id > 0) {
            memo = mDaoSession.getMemoDao().load(id);
        } else {
            memo = new Memo();
        }
        RxBus.get().post(BusAction.CMEMO_SET_MEMO, memo);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_CMEMO_TITLE)})
    public void setActivityTitle(String title) {
        setTitle(title);
    }

    @Subscribe(tags = {@Tag(BusAction.CMEMO_PICK_IMAGE)})
    public void showPicturePicker(String s) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            RxBus.get().post(BusAction.CMEMO_IMAGE, uri);
        }
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.SAVE_MEMO)})
    public void saveMemo(Memo memo) {
        Long key = memo.getId();
        DatasetChangedEvent event = new DatasetChangedEvent();
        if (key != null) {
            event.setAction(DatasetChangedEvent.UPDATE);
        } else {
            event.setAction(DatasetChangedEvent.INSERT);
        }
        if (CommonUtils.isBackward(memo.getRemindTime())) {
            setReminder(memo);
        }
        mDaoSession.getMemoDao().save(memo);
        RxBus.get().post(BusAction.MEMO_DATASET_CHANGED, event);
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.DELETE_MEMO)})
    public void deleteMemo(Memo memo) {
        removeReminder(memo);
        mDaoSession.getMemoDao().delete(memo);
        DatasetChangedEvent event = new DatasetChangedEvent(DatasetChangedEvent.DELETE);
        RxBus.get().post(BusAction.MEMO_DATASET_CHANGED, event);
    }

    private void setReminder(Memo memo) {
        Reminder reminder = ReminderUtils.getReminder(this, memo,
                NavigationUtils.memo(this, memo));
        ReminderUtils.scheduleReminder(this, reminder);
    }

    private void removeReminder(Memo memo) {
        Reminder reminder = ReminderUtils.getReminder(this, memo,
                NavigationUtils.memo(this, memo));
        ReminderUtils.cancelReminder(this, reminder);
    }
}
