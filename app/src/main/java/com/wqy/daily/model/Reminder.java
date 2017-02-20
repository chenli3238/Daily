package com.wqy.daily.model;

import android.content.Intent;

import java.util.function.Supplier;

/**
 * Created by wqy on 17-2-6.
 */

public class Reminder {

    private int id;

    private long time;

    private String title;

    private String content;

    private Intent mIntent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }
}
