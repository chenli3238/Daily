package com.wqy.daily.event;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * Created by wqy on 17-2-9.
 */

public class ShowDialogEvent {

    private String mTag;
    private DialogFragment mFragment;


    public ShowDialogEvent(String tag, DialogFragment fragment) {
        mTag = tag;
        mFragment = fragment;
    }

    public DialogFragment getFragment() {
        return mFragment;
    }

    public void setFragment(DialogFragment fragment) {
        mFragment = fragment;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }
}
