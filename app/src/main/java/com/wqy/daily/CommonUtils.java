package com.wqy.daily;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wqy on 17-2-7.
 */

public class CommonUtils {

    public static int backwardDays(Date date) {
        Calendar today = Calendar.getInstance();
        long delta = date.getTime() - today.getTimeInMillis();
        return (int) (delta / (1000 * 3600 * 24));
    }

    public static int forwardDays(Date date) {
        Calendar today = Calendar.getInstance();
        long delta = today.getTimeInMillis() - date.getTime();
        return (int) (delta / (1000 * 3600 * 24));
    }
}
