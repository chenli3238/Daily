package com.wqy.daily.event;

/**
 * Created by wqy on 17-2-10.
 */

public class DatasetChangedEvent {

    public static final int INSERT = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;

    private int action;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public DatasetChangedEvent(int action) {
        this.action = action;
    }

    public DatasetChangedEvent() {
    }
}
