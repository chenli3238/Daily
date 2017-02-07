package com.wqy.daily.event;

import com.wqy.daily.model.Bigday;

import java.util.List;

/**
 * Created by wqy on 17-2-7.
 */

public class BigdayEvent {

    List<Bigday> mBigdays;

    public BigdayEvent(List<Bigday> bigdays) {
        mBigdays = bigdays;
    }

    public List<Bigday> getBigdays() {
        return mBigdays;
    }

    public void setBigdays(List<Bigday> bigdays) {
        mBigdays = bigdays;
    }
}
