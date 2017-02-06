package com.wqy.daily.model;

/**
 * Created by wqy on 17-2-6.
 */

public class Event {

    private String title;

    private String desc;

    private Priority priority;

    private float keepRate;

    private boolean keepTime;

    private boolean finished;

    private boolean deleted;

    private Reminder reminder;

    public enum Priority {

        LOW,

        MEDIUM,

        HIGH
    }
}
