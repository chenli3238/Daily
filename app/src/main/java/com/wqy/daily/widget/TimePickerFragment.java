package com.wqy.daily.widget;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.hwangjr.rxbus.RxBus;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.TimePickerEvent;

import java.util.Calendar;

/**
 * Created by wqy on 17-2-8.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public static final String TAG = TimePickerFragment.class.getSimpleName();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                RxBus.get().post(BusAction.TIME_PICKER_RESULT, new TimePickerEvent(hourOfDay, minute));
            }
        }, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        RxBus.get().post(BusAction.TIME_PICKER_RESULT,
                new TimePickerEvent(hourOfDay, minute));
    }
}
