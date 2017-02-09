package com.wqy.daily.model;

import java.util.List;

/**
 * Created by wqy on 17-2-8.
 */

public class DayTime {

    private List<Integer> days;

    private int hour;

    private int minute;

    public DayTime(List<Integer> days, int hour, int minute) {
        this.days = days;
        this.hour = hour;
        this.minute = minute;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
