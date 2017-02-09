package com.wqy.daily.event;

/**
 * Created by wqy on 17-2-9.
 */

public class NumberPickerEvent {

    public static final int MAX_VALUE = -1;

    private int number;

    public NumberPickerEvent(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
