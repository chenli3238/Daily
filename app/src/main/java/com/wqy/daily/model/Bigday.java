package com.wqy.daily.model;

import java.util.Date;

/**
 * Created by wqy on 17-2-6.
 */

public class Bigday {

    private Date date;

    private String title;

    private String desc;

    private String tags;

    private boolean remindme;

    private Reminder reminder;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isRemindme() {
        return remindme;
    }

    public void setRemindme(boolean remindme) {
        this.remindme = remindme;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }
}
