package com.wqy.daily.model;

import android.net.Uri;

/**
 * Created by wqy on 17-2-23.
 */

public class SpanInfo {
    private int start;
    private int end;
    private Uri mUri;

    public SpanInfo() {
    }

    public SpanInfo(int start, int end, Uri uri) {
        this.start = start;
        this.end = end;
        mUri = uri;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }
}
