package com.wqy.daily.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.hwangjr.rxbus.RxBus;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wqy on 17-2-9.
 */

public class TagPickerFragment extends DialogFragment {

    public static final String TAG = TagPickerFragment.class.getSimpleName();
    public static final String ARG_EVENT_TAG = "EVENT_TAG";

    private View mView;
    private TagView mTagView;
    private List<String> mItems;
    private ArrayAdapter<String> mAdapter;
    private String mTagEvent;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.picker_tag, null);
        mTagView = (TagView) mView.findViewById(R.id.picker_auto_complete);
        // TODO: 17-2-9 get tags from database
        mItems = Arrays.asList("恋爱", "学习", "浪浪浪～", "lianai", "xuexi", "langlanglang~");
        mAdapter = new ArrayAdapter<>(getContext(), R.layout.tag_view, mItems);
        mTagView.setAdapter(mAdapter);
//        mTagView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        mTagView.setSplitChar(new char[] {' ', ',', ';'});
        mTagView.allowDuplicates(false);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(mView)
                .setPositiveButton(R.string.cpunch_confirm, (dialog, which) -> {
                    confirm();
                });
        return builder.create();
    }

    private void confirm() {
        Log.d(TAG, "confirm: ");

        List<String> objs = mTagView.getObjects();
        String[] tags = objs.toArray(new String[0]);
        mTagEvent = getArguments().getString(ARG_EVENT_TAG,
                BusAction.TAG_PICKER_RESULT);
        RxBus.get().post(mTagEvent, tags);
    }
}
