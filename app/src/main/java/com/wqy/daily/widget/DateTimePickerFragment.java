package com.wqy.daily.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.hwangjr.rxbus.RxBus;
import com.wqy.daily.R;
import com.wqy.daily.adapter.ListPagerAdapter;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.DateTimeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wqy on 17-2-9.
 */

public class DateTimePickerFragment extends ViewPagerPickerFragment {
    public static final String TAG = DateTimePickerFragment.class.getSimpleName();
    public static final String ARG_EVENT_TAG = "EVENT_TAG";

    private TimePicker mTimePicker;
    private DatePicker mDatePicker;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    Calendar mCalendar;

    private String mEventTag;

    @Override
    protected void confirm() {
        mYear = mDatePicker.getYear();
        mMonth = mDatePicker.getMonth();
        mDay = mDatePicker.getDayOfMonth();

        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);

        mEventTag = getArguments().getString(ARG_EVENT_TAG,
                BusAction.DATE_TIME_PICKER_RESULLT);
        // TODO: 17-2-10 use ARG_EVENT_TAG to replace this action
        RxBus.get().post(mEventTag, mCalendar.getTime());
    }


    @Override
    protected List<View> getViews() {
        mCalendar = Calendar.getInstance();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mTimePicker = (TimePicker) inflater.inflate(R.layout.time_picker, null);
        mTimePicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(0);
            mTimePicker.setMinute(0);
        } else {
            mTimePicker.setCurrentHour(0);
            mTimePicker.setCurrentMinute(0);
        }
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mDatePicker = (DatePicker) inflater.inflate(R.layout.date_picker, null);
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mTimePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            mHour = hourOfDay;
            mMinute = minute;
        });
        return Arrays.asList(mDatePicker, mTimePicker);
    }
}
