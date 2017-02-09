package com.wqy.daily.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.hwangjr.rxbus.RxBus;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.NumberPickerEvent;

import java.util.Objects;

/**
 * Created by wqy on 17-2-9.
 */

public class NumberPickerFragment extends DialogFragment {
    public static final String TAG = NumberPickerFragment.class.getSimpleName();

    private static int MAX_VALUE;
    View mView;

    NumberPicker mNumberPicker;
    String[] mDisplayedValues;

    public NumberPickerFragment() {
        Bundle bundle = getArguments();
        mDisplayedValues = bundle.getStringArray("values");
        MAX_VALUE = bundle.getInt("maxValue", mDisplayedValues.length - 1);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.picker_number_picker, null);
        mNumberPicker = (NumberPicker) mView.findViewById(R.id.picker_number);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(mView)
                .setPositiveButton(R.string.cpunch_confirm, (dialog, which) -> {
                    int value = mNumberPicker.getValue();
                    NumberPickerEvent event;
                    if (value == MAX_VALUE) {
                        event = new NumberPickerEvent(NumberPickerEvent.MAX_VALUE);
                    } else {
                        event = new NumberPickerEvent(Integer.valueOf(mDisplayedValues[value]));
                    }
                    RxBus.get().post(BusAction.NUMBER_PICKER_RESULT, event);
                });
        mNumberPicker.setDisplayedValues(mDisplayedValues);
        mNumberPicker.setMaxValue(MAX_VALUE);
        return builder.create();
    }
}
