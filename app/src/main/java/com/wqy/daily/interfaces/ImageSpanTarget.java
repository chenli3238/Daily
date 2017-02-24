package com.wqy.daily.interfaces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wqy.daily.StringUtils;
import com.wqy.daily.view.CreateMemoView;

/**
 * Created by wqy on 17-2-23.
 */

public class ImageSpanTarget implements Target {
    public static final String TAG = ImageSpanTarget.class.getSimpleName();

    private Context mContext;
    protected String mText;
    protected ImageSpan mImageSpan;
    protected SpannableString mSpannableString;
    protected int mMaxWidth;
    protected int mMaxHeight;
    protected OnImageSetListener mOnImageSetListener;

    public ImageSpanTarget(Context context, String text) {
        mContext = context;
        mText = text;
        mSpannableString = new SpannableString(mText);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Log.d(TAG, "onBitmapLoaded: ");
        ImageSpan newSpan = StringUtils.getSpan(new BitmapDrawable(mContext.getResources(), bitmap));
        mSpannableString.setSpan(newSpan, 0, mText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        dispatchResult();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        Log.d(TAG, "onPrepareLoad: ");
        ImageSpan newSpan = StringUtils.getSpan(placeHolderDrawable);
        mSpannableString.setSpan(newSpan, 0, mText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        dispatchResult();
    }

    public int getMaxHeight() {
        return mMaxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        mMaxHeight = maxHeight;
    }

    public int getMaxWidth() {
        return mMaxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        mMaxWidth = maxWidth;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public ImageSpan getImageSpan() {
        return mImageSpan;
    }

    public void setImageSpan(ImageSpan imageSpan) {
        mImageSpan = imageSpan;
    }

    public SpannableString getSpannableString() {
        return mSpannableString;
    }

    public void setSpannableString(SpannableString spannableString) {
        mSpannableString = spannableString;
    }

    public OnImageSetListener getOnImageSetListener() {
        return mOnImageSetListener;
    }

    public void setOnImageSetListener(OnImageSetListener onImageSetListener) {
        mOnImageSetListener = onImageSetListener;
    }

    public void dispatchResult() {
        if (mOnImageSetListener != null) {
            mOnImageSetListener.onImageSet(this);
        }
    }

    public interface OnImageSetListener {
        void onImageSet(ImageSpanTarget target);
    }
}
