package com.wqy.daily;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;

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
    }

    @Test
    public void testDeltaDayFromToday() {
        Calendar c1 = Calendar.getInstance();

        assertEquals(0, CommonUtils.deltaDayFromToday(c1.getTime()));

        c1.add(Calendar.DAY_OF_YEAR, 1);
        assertEquals(1, CommonUtils.deltaDayFromToday(c1.getTime()));
        c1.add(Calendar.DAY_OF_YEAR, 1);
        assertEquals(2, CommonUtils.deltaDayFromToday(c1.getTime()));

        c1.add(Calendar.DAY_OF_YEAR, -3);
        assertEquals(-1, CommonUtils.deltaDayFromToday(c1.getTime()));
        c1.add(Calendar.DAY_OF_YEAR, -1);
        assertEquals(-2, CommonUtils.deltaDayFromToday(c1.getTime()));
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
}
