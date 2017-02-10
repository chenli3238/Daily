package com.wqy.daily.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqy on 17-2-8.
 */

public class DayTime {

    private static final String DIVIDER = " ";

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = days == null ? 0 : days.size();
        for (int i = 0; i < size; i++) {
            sb.append(days.get(i)).append(DIVIDER);
        }
        sb.append(hour).append(DIVIDER).append(minute);
        return sb.toString();
    }

    public static DayTime fromString(String s) {
        String[] a = s.trim().split(DIVIDER);
        int size = a.length - 2;
        if (size <= 0) return null;
        List<Integer> days = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            days.add(Integer.valueOf(a[i]));
        }

        int hour = Integer.valueOf(a[size]);
        int minute = Integer.valueOf(a[size + 1]);
        return new DayTime(days, hour, minute);
    }
}
