package com.wqy.daily;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by wqy on 17-2-19.
 */
public class CommonUtilsTest {

    @Test
    public void testGetTodayBegin() {
        Calendar today = CommonUtils.getTodayBegin();
        Calendar now = Calendar.getInstance();
        assertEquals(now.get(Calendar.DAY_OF_YEAR), today.get(Calendar.DAY_OF_YEAR));
        assertEquals(0, today.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, today.get(Calendar.MINUTE));
        assertEquals(0, today.get(Calendar.SECOND));

        assertEquals(today.getTimeInMillis(), CommonUtils.getDateBegin(now).getTimeInMillis());
        assertEquals(today.getTimeInMillis(), CommonUtils.getDateBegin(new Date()).getTime());
    }

    @Test
    public void testIsBackward() {
        Calendar c1 = Calendar.getInstance();
        assertTrue(CommonUtils.isBackward(c1.getTime()));

        c1.add(Calendar.DAY_OF_YEAR, -1);
        assertFalse(CommonUtils.isBackward(c1.getTime()));

        Calendar c2 = CommonUtils.getTodayBegin();
        assertTrue(CommonUtils.isBackward(c2.getTime()));
    }

    @Test
    public void testDeltaDay() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date d1 = format.parse("2017-2-18 00:00");
        Date d2 = format.parse("2017-2-19 20:00");
        assertEquals(0, CommonUtils.deltaDay(d1, d1));
        assertEquals(0, CommonUtils.deltaDay(d2, d2));
        assertEquals(1, CommonUtils.deltaDay(d1, d2));
        assertEquals(1, CommonUtils.deltaDay(d2, d1));

        Date d3 = format.parse("2017-2-20 8:00");
        assertEquals(2, CommonUtils.deltaDay(d1, d3));
        assertEquals(1, CommonUtils.deltaDay(d2, d3));
    }

    @Test
    public void testDeltaDayWithToday() {
        Calendar c1 = Calendar.getInstance();

        assertEquals(0, CommonUtils.deltaDayWithToday(c1.getTime()));

        c1.add(Calendar.DAY_OF_YEAR, 1);
        assertEquals(1, CommonUtils.deltaDayWithToday(c1.getTime()));
        c1.add(Calendar.DAY_OF_YEAR, 1);
        assertEquals(2, CommonUtils.deltaDayWithToday(c1.getTime()));

        c1.add(Calendar.DAY_OF_YEAR, -3);
        assertEquals(1, CommonUtils.deltaDayWithToday(c1.getTime()));
        c1.add(Calendar.DAY_OF_YEAR, -1);
        assertEquals(2, CommonUtils.deltaDayWithToday(c1.getTime()));

        Calendar c2 = CommonUtils.getTodayBegin();
        assertEquals(0, CommonUtils.deltaDayWithToday(c2.getTime()));
    }
}
