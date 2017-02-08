package com.wqy.daily.event;

/**
 * Created by wqy on 17-2-8.
 */

public class TimePickerEvent {

    public int hourOfDay;

    public int minute;

    public TimePickerEvent(int hourOfDay, int minute) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }
}
