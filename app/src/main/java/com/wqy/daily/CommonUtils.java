package com.wqy.daily;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.util.TimeUtils;

import com.wqy.daily.model.DayTime;
import com.wqy.daily.event.NumberPickerEvent;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wqy on 17-2-7.
 */

public class CommonUtils {

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar getDateBegin(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Date getDateBegin(Date date) {
        Calendar c = getCalendar(date);
        return getDateBegin(c).getTime();
    }

    public static Calendar getTodayBegin() {
        Calendar c = Calendar.getInstance();
        return getDateBegin(c);
    }

    public static boolean isBackward(Date date) {
        if (date == null) return false;
        Date today = new Date();
        long delta = (date.getTime() - today.getTime());
        return delta >= 0;
    }

    public static int deltaDayWithToday(Date date) {
        return deltaDay(new Date(), date);
    }

    public static int deltaDay(Date start, Date end) {
        Date d1 = getDateBegin(start);
        Date d2 = getDateBegin(end);
        long delta = d2.getTime() - d1.getTime();
        if (delta < 0) delta = -delta;
        delta = delta / (1000 * 3600 * 24);
        return (int) delta;
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

    public static String getDayTimeString(@NonNull Resources resources, @NonNull DayTime event) {
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

    public static String getBooleanString(@NonNull Resources resources, @NonNull Boolean b) {
        if (b) {
            return resources.getString(R.string.yes);
        } else {
            return resources.getString(R.string.no);
        }
    }

    public static String getTagString(@NonNull Resources resources, @NonNull String[] tags) {
        String divider = resources.getString(R.string.comma);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tags.length; i++) {
            sb.append(tags[i]);
            if (i < tags.length - 1) {
                sb.append(divider);
            }
        }
        return sb.toString();
    }

    public static String[] splitTagString(@NonNull Resources resources, @NonNull String tags) {
        String divider = resources.getString(R.string.comma);
        return tags.split(divider);
    }

    public static String getDateTimeString(@NonNull Resources resources, Date date) {
        if (date == null) return null;
        SimpleDateFormat format = new SimpleDateFormat(resources.getString(R.string.format_date_time));
        return format.format(date);
    }
}
