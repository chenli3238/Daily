package com.wqy.daily.widget;

import android.app.Dialog;
import android.content.DialogInterface;
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

import com.wqy.daily.R;
import com.wqy.daily.adapter.ListPagerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wqy on 17-2-9.
 */

public abstract class ViewPagerPickerFragment extends DialogFragment {
    public static final String TAG = ViewPagerPickerFragment.class.getSimpleName();

    private ListPagerAdapter mAdapter;
    private List<View> mViews;
    private ViewPager mViewPager;
    private Button mPositiveButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mViewPager = (ViewPager) inflater.inflate(R.layout.view_pager, null);
        mViews = getViews();
        mAdapter = new ListPagerAdapter(mViews, null);
        mViewPager.setAdapter(mAdapter);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(mViewPager)
                .setPositiveButton(R.string.cpunch_confirm, (dialog1, which) -> {

                })
                .create();
        dialog.setOnShowListener(dialog12 -> {
            mPositiveButton = ((AlertDialog) dialog12).getButton(AlertDialog.BUTTON_POSITIVE);
            mPositiveButton.setOnClickListener(v -> {
                Log.d(TAG, "onClick: ");
                if (lastPage()) {
                    confirm();
                    dialog12.dismiss();
                } else {
                    moveNext();
                }
            });
        });

        return dialog;
    }

    protected abstract void confirm();

    protected abstract List<View> getViews();


    private boolean lastPage() {
        return mViewPager.getCurrentItem() == mAdapter.getCount() - 1;
    }

    private void moveNext() {
        mViewPager.setCurrentItem(
                mViewPager.getCurrentItem() + 1
        );
    }
}
