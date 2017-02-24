package com.wqy.daily.interfaces;

import android.net.Uri;
import android.text.style.ImageSpan;

import com.squareup.picasso.Target;

/**
 * Created by wqy on 17-2-23.
 */

public interface ImageLoader {
    void load(Uri uri, ImageSpanTarget target);
}
