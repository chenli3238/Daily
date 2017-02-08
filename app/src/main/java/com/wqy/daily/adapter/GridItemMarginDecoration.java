package com.wqy.daily.adapter;

import android.graphics.Rect;
import android.view.View;

import com.wqy.daily.widget.RecyclerView;

/**
 * Created by wqy on 17-2-6.
 */

public class GridItemMarginDecoration extends RecyclerView.ItemDecoration {

    private int mSpanCount;

    private int mMargin;

    public GridItemMarginDecoration(int spanCount, int margin) {
        mSpanCount = spanCount;
        mMargin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, android.support.v7.widget.RecyclerView parent, android.support.v7.widget.RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (position < mSpanCount) {
            outRect.top = mMargin;
        }
        if (position % mSpanCount == 0) {
            outRect.left = mMargin;
        }
        outRect.right = mMargin;
        outRect.bottom = mMargin;
    }
}
