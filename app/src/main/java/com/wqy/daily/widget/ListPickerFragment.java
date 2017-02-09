package com.wqy.daily.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.ihidea.multilinechooselib.MultiLineChooseLayout;
import com.wqy.daily.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wqy on 17-2-9.
 */

public class ListPickerFragment extends DialogFragment {

    public static final String TAG = ListPickerFragment.class.getSimpleName();

    public static final String ARG_TITLE = "TITLE";

    // require
    public static final String ARG_ITEM_TITLES = "ITEM_TITLES";

    // require
    public static final String ARG_EVENT_TAG = "EVENT_TAG";

    public static final String ARG_DEFAULT_VALUE = "DEFAULT_VALUE";

    private String mTitle;

    private List<String> mItemTitles;

    private View mView;

    private MultiLineChooseLayout mMultiChoose;

    private String mEventTag;

    private int mDefaultValue;

    public ListPickerFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        init();
        mView = LayoutInflater.from(getContext()).inflate(R.layout.picker_list, null);
        mMultiChoose = (MultiLineChooseLayout) mView.findViewById(R.id.picker_list);
        mMultiChoose.setList(mItemTitles);
        mMultiChoose.setIndexItemSelected(mDefaultValue);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(mView)
                .setPositiveButton(R.string.cpunch_confirm, (dialog, which) -> {
                    int selected = mMultiChoose.getSelectedIndex();
                    if (selected == -1) {
                        dialog.cancel();
                    } else {
                        RxBus.get().post(mEventTag, selected);
                    }
                });
        if (mTitle != null) {
            builder.setTitle(mTitle);
        }
        return builder.create();
    }

    void init() {
        Bundle args = getArguments();
        mTitle = args.getString(ARG_TITLE);
        mItemTitles = Arrays.asList(args.getStringArray(ARG_ITEM_TITLES));
        if (mItemTitles == null) {
            mItemTitles = args.getStringArrayList(ARG_ITEM_TITLES);
        }
        mEventTag = args.getString(ARG_EVENT_TAG);
        mDefaultValue = args.getInt(ARG_DEFAULT_VALUE, 0);
        Log.d(TAG, "init: eventTag = " + mEventTag);
    }
}
