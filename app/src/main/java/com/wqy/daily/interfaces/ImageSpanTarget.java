package com.wqy.daily.interfaces;

import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.squareup.picasso.Target;
import com.wqy.daily.view.CreateMemoView;

/**
 * Created by wqy on 17-2-23.
 */

public abstract class ImageSpanTarget implements Target {
    public static final String TAG = ImageSpanTarget.class.getSimpleName();

    protected String mText;
    protected ImageSpan mImageSpan;
    protected SpannableString mSpannableString;

    public ImageSpanTarget(String text) {
        mText = text;
        mSpannableString = new SpannableString(mText);
    }
}
