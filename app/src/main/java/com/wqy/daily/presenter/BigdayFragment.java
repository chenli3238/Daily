package com.wqy.daily.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.App;
import com.wqy.daily.BaseFragment;
import com.wqy.daily.CommonUtils;
import com.wqy.daily.R;
import com.wqy.daily.adapter.BigdayInitEvent;
import com.wqy.daily.event.BigdayEvent;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.model.Bigday;
import com.wqy.daily.model.BigdayDao;
import com.wqy.daily.model.DaoSession;
import com.wqy.daily.mvp.IView;
import com.wqy.daily.view.BigdayView;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BigdayFragment extends BaseFragment {

    private DaoSession mDaoSession;
    private List<Bigday> mBackwards = null;
    private List<Bigday> mFordwards = null;

    public static final String TAG = BigdayFragment.class.getSimpleName();

    public BigdayFragment() {
    }

    @Override
    public IView getIView() {
        return new BigdayView();
    }

    @Override
    public void created(Bundle savedInstanceState) {
        mDaoSession = ((App) getActivity().getApplicationContext()).getDaoSession();
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    private Bigday createBigday(boolean backward) {
        Bigday bigday = new Bigday();
        bigday.setTitle("高考");
        Calendar calendar = Calendar.getInstance();
        int offset = (int) (Math.random() * 365);
        if (!backward) offset = -offset;
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        bigday.setDate(calendar.getTime());
        return bigday;
    }

    private List<Bigday> createBigday(int n, boolean backward) {
        List<Bigday> bigdays = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            bigdays.add(createBigday(backward));
        }
        return bigdays;
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.INIT_BIGDAY_BACKWARD)})
    public void getBigdayBackward(BigdayInitEvent event) {
        if (mBackwards == null) {
            mBackwards = mDaoSession.getBigdayDao().queryBuilder()
                    .where(BigdayDao.Properties.Date.gt(
                            CommonUtils.getTodayBegin().getTime()
                    )).list();
        }
        RxBus.get().post(BusAction.SET_BIGDAY_BACKWARD,
                new BigdayEvent(mBackwards));
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.INIT_BIGDAY_FORWARD)})
    public void getBigdayForward(BigdayInitEvent event) {
        if (mFordwards == null) {
            mFordwards = mDaoSession.getBigdayDao().queryBuilder()
                    .where(BigdayDao.Properties.Date.lt(
                            CommonUtils.getTodayBegin().getTime()
                    )).list();
        }
        RxBus.get().post(BusAction.SET_BIGDAY_FORWARD,
                new BigdayEvent(mFordwards));
    }

    @Subscribe(tags = {@Tag(BusAction.CREATE_BIGDAY)})
    public void createBigday(String s) {
        Intent intent = new Intent(getContext(), CreateBigdayActivity.class);
        intent.setAction(getString(R.string.action_edit));
        startActivity(intent);
    }

    @Subscribe(tags = {@Tag(BusAction.VIEW_BIGDAY)})
    public void viewBigday(Bigday bigday) {
        Intent intent = new Intent(getContext(), CreateBigdayActivity.class);
        intent.setAction(getString(R.string.action_view));
        intent.putExtra(CreateBigdayActivity.EXTRA_BIGDAY_ID, bigday.getId());
    }
}
