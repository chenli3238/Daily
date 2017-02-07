package com.wqy.daily;

import android.content.res.Resources;

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

    public static String getDayOfWeekString(Resources resources, int dayOfWeek) {
        switch (dayOfWeek) {
            case 1: return resources.getString(R.string.date_mon);
            case 2: return resources.getString(R.string.date_tue);
            case 3: return resources.getString(R.string.date_wed);
            case 4: return resources.getString(R.string.date_thu);
            case 5: return resources.getString(R.string.date_fri);
            case 6: return resources.getString(R.string.date_sat);
            case 7: return resources.getString(R.string.date_sun);
            default: return "";
        }
    }
}
