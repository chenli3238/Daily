package com.wqy.daily.event;

/**
 * Created by wqy on 17-2-10.
 */

public class DatasetChangedEvent<T> {

    public static final int INSERT = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;

    private int action;
    private T[] keys;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public T[] getKeys() {
        return keys;
    }

    public void setKeys(T[] keys) {
        this.keys = keys;
    }

    public DatasetChangedEvent(int action, T[] keys) {
        this.action = action;
        this.keys = keys;
    }

    public DatasetChangedEvent() {
    }
}
