package com.wqy.daily.model;

/**
 * Created by wqy on 17-2-6.
 */

public class Pager {

    private long limit;

    private long lastId;

    public Pager() {}

    public Pager(long limit, long lastId) {
        this.limit = limit;
        this.lastId = lastId;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public long getLastId() {
        return lastId;
    }

    public void setLastId(long lastId) {
        this.lastId = lastId;
    }
}
