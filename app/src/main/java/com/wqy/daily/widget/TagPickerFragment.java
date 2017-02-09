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
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import com.hwangjr.rxbus.RxBus;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wqy on 17-2-9.
 */

public class TagPickerFragment extends DialogFragment {

    public static final String TAG = TagPickerFragment.class.getSimpleName();

    private View mView;
    private MultiAutoCompleteTextView mCompleteTextView;
    private List<String> mItems;
    private ArrayAdapter<String> mAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.picker_tag, null);
        mCompleteTextView = (MultiAutoCompleteTextView) mView.findViewById(R.id.picker_auto_complete);
        // TODO: 17-2-9 get tags from database
        mItems = Arrays.asList("恋爱", "学习", "浪浪浪～", "lianai", "xuexi", "langlanglang~");
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mItems);
        mCompleteTextView.setAdapter(mAdapter);
        mCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(mView)
                .setPositiveButton(R.string.cpunch_confirm, (dialog, which) -> {
                    confirm();
                });
        return builder.create();
    }

    private void confirm() {
        Log.d(TAG, "confirm: ");
        String text = mCompleteTextView.getText().toString().trim();
        String[] tags = text.split(getString(R.string.comma));
        for (int i = 0; i < tags.length; i++) {
            tags[i] = tags[i].trim();
        }
        RxBus.get().post(BusAction.TAG_PICKER_RESULT, tags);
    }
}
