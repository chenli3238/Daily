package com.wqy.daily.event;

import com.wqy.daily.model.Bigday;

import java.util.List;

/**
 * Created by wqy on 17-2-7.
 */

public class BigdayEvent {

    public static final int LOAD_MORE = 1;
    public static final int REFRESH = 2;

    private int action;

    private boolean noMore = false;

    private List<Bigday> mBigdays;

    public BigdayEvent() {
    }

    public BigdayEvent(int action) {
        this.action = action;
    }

    public List<Bigday> getBigdays() {
        return mBigdays;
    }

    public void setBigdays(List<Bigday> bigdays) {
        mBigdays = bigdays;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public boolean isNoMore() {
        return noMore;
    }

    public void setNoMore(boolean noMore) {
        this.noMore = noMore;
    }
}
