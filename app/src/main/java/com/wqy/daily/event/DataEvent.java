package com.wqy.daily.event;

import com.wqy.daily.model.Bigday;

import java.util.List;

/**
 * Created by wqy on 17-2-7.
 */

public class DataEvent<T> {

    public static final int LOAD_MORE = 1;
    public static final int REFRESH = 2;

    private int action;

    private boolean hasMore = true;

    private List<T> mDatas;

    public DataEvent() {
    }

    public DataEvent(int action) {
        this.action = action;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
