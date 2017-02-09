package com.wqy.daily.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.hwangjr.rxbus.RxBus;
import com.wqy.daily.R;

/**
 * Created by wqy on 17-2-9.
 */

public class BooleanPickerFragment extends DialogFragment {
    public static final String TAG = BooleanPickerFragment.class.getSimpleName();

    public static final String ARG_MESSAGE = "MESSAGE";

    // required
    public static final String ARG_EVENT_TAG = "EVENT_TAG";

    private String mMessage;
    private String mEventTag;

    public BooleanPickerFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mMessage = bundle.getString(ARG_MESSAGE);
        mEventTag = bundle.getString(ARG_EVENT_TAG);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(mMessage)
                .setPositiveButton(getString(R.string.yes),
                        (dialog, which) -> onResult(Boolean.TRUE))
                .setNegativeButton(getString(R.string.no),
                        (dialog, which) -> onResult(Boolean.FALSE));
        return builder.create();
    }

    private void onResult(Boolean b) {
        RxBus.get().post(mEventTag, b);
    }
}
