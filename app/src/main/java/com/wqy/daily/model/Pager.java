package com.wqy.daily.model;

/**
 * Created by wqy on 17-2-6.
 */

public class Pager {

    public static final long MIN_ID = 0;

    public static final int DEFAULT_PAGE_SIZE = 10;

    private int limit;

    private int offset;

    private long lastId;

    public Pager() {
        this(DEFAULT_PAGE_SIZE);
    }

    public Pager(int limit) {
        this.limit = limit;
        lastId = MIN_ID;
        offset = 0;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getLastId() {
        return lastId;
    }

    public void setLastId(long lastId) {
        this.lastId = lastId;
    }

    public void reset() {
        lastId = MIN_ID;
        offset = 0;
    }

    public boolean loadingFirstPage() {
        return lastId == MIN_ID;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void addOffset(int offset) {
        this.offset += offset;
    }
}
