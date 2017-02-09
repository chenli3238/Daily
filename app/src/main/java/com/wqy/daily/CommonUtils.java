package com.wqy.daily;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.wqy.daily.event.DayTimePickerEvent;
import com.wqy.daily.event.NumberPickerEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
            case 1:
                return resources.getString(R.string.date_mon);
            case 2:
                return resources.getString(R.string.date_tue);
            case 3:
                return resources.getString(R.string.date_wed);
            case 4:
                return resources.getString(R.string.date_thu);
            case 5:
                return resources.getString(R.string.date_fri);
            case 6:
                return resources.getString(R.string.date_sat);
            case 7:
                return resources.getString(R.string.date_sun);
            default:
                return "";
        }
    }

    public static String getDayTimeString(@NonNull Resources resources, @NonNull DayTimePickerEvent event) {
        String[] a = resources.getStringArray(R.array.day_of_week);
        String divider = resources.getString(R.string.comma);

        StringBuilder sb = new StringBuilder();
        List<Integer> days = event.getDays();
        int size = days == null ? 0 : days.size();

        if (size == 7) {
            sb.append(resources.getString(R.string.date_everyday));
        } else {
            for (int i = 0; i < size; i++) {
                sb.append(a[days.get(i)]);
                if (i < size - 1) {
                    sb.append(divider);
                }
            }
        }
        return String.format("%s  %d:%d", sb.toString(), event.getHour(), event.getMinute());
    }

    public static String getNumberString(@NonNull Resources resources, @NonNull NumberPickerEvent event) {
        if (event.getNumber() == NumberPickerEvent.MAX_VALUE) {
            return resources.getString(R.string.cpunch_max);
        }
        return String.valueOf(event.getNumber());
    }
}
