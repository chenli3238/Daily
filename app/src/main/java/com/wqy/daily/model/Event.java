package com.wqy.daily.model;

/**
 * Created by wqy on 17-2-6.
 */

public class Event {

    public static final int ENDLESS = -1;

    private String title;

    private String desc;

    private DayTime punchTime;

    private Priority priority;

    /**
     * 目标打卡次数, -1 表示不限次数。
     */
    private int aim;

    private float keepRate;

    private boolean keepTime;

    private boolean finished;

    private boolean deleted;

    private float score;

    private boolean remind;

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

    public DayTime getPunchTime() {
        return punchTime;
    }

    public void setPunchTime(DayTime punchTime) {
        this.punchTime = punchTime;
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

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
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

    public void setEndless() {
        aim = ENDLESS;
    }

    public int getAim() {
        return aim;
    }

    public void setAim(int aim) {
        this.aim = aim;
    }

    public boolean isEndless() {
        return aim == ENDLESS;

    }

    public enum Priority {

        LOW,

        MEDIUM,

        HIGH
    }
}
