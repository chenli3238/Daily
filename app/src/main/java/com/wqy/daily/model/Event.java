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

    private float score;

    private Reminder reminder;

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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isKeepTime() {
        return keepTime;
    }

    public void setKeepTime(boolean keepTime) {
        this.keepTime = keepTime;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public float getKeepRate() {
        return keepRate;
    }

    public void setKeepRate(float keepRate) {
        this.keepRate = keepRate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public enum Priority {

        LOW,

        MEDIUM,

        HIGH
    }
}
