package com.wqy.daily.widget;

import android.support.v4.app.DialogFragment;

/**
 * Created by wqy on 17-2-8.
 */

public class NoticeDialogFragment extends DialogFragment {

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

    protected NoticeDialogListener mNoticeDialogListener;

    public NoticeDialogListener getNoticeDialogListener() {
        return mNoticeDialogListener;
    }

    public void setNoticeDialogListener(NoticeDialogListener noticeDialogListener) {
        mNoticeDialogListener = noticeDialogListener;
    }
}
