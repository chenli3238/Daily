package com.wqy.daily.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.hwangjr.rxbus.RxBus;
import com.ihidea.multilinechooselib.MultiLineChooseLayout;
import com.wqy.daily.R;
import com.wqy.daily.adapter.ListPagerAdapter;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.DayTimePickerEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wqy on 17-2-8.
 */

public class DayTimePickerFragment extends NoticeDialogFragment {

    public static final String TAG = DayTimePickerFragment.class.getSimpleName();

    View mView;
    Button mPositiveButton;
    Button mNeutralButton;
    TimePicker mTimePicker;
    MultiLineChooseLayout mMultiLineChooseLayout;

    PagerAdapter mAdapter;

    List<Integer> mDays;

    int mHour;

    int mMinute;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDays = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mView = inflater.inflate(R.layout.picker_day_time, null);
        mMultiLineChooseLayout = (MultiLineChooseLayout) mView.findViewById(R.id.picker_dt_mul);
        mMultiLineChooseLayout.setList(getResources().getStringArray(R.array.day_of_week));
        setDefault();
        mTimePicker = (TimePicker) mView.findViewById(R.id.picker_dt_time);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(mView)
                .setPositiveButton(R.string.cpunch_confirm, (dialog12, which) -> {
                    confirm();
                }).create();

        mTimePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            Log.d(TAG, "onTimeChanged: hourOfDay = " + hourOfDay + " minute = " + minute);
            mHour = hourOfDay;
            mMinute = minute;
        });

        return dialog;
    }

    void setDefault() {
        for (int i = 0; i < 5; i++) {
            mMultiLineChooseLayout.setIndexItemSelected(i);
        }

        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    }

    void confirm() {
        mDays = mMultiLineChooseLayout.getAllItemSelectedIndex();
        DayTimePickerEvent event = new DayTimePickerEvent(mDays, mHour, mMinute);
        RxBus.get().post(BusAction.DAY_TIME_PICKER_RESULT, event);
    }
}
