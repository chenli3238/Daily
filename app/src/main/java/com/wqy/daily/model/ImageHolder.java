package com.wqy.daily.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by wqy on 17-2-20.
 */

public class ImageHolder implements Target {
    private Context mContext;
    private Drawable mDrawable;
    private Bitmap mBitmap;

    public ImageHolder(Context context) {
        mContext = context;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        mBitmap = bitmap;
        mDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        mDrawable = errorDrawable;
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        mDrawable = placeHolderDrawable;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }
}
