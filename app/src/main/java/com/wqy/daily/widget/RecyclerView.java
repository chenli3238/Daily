package com.wqy.daily.widget;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wqy on 17-2-6.
 */

public class RecyclerView extends android.support.v7.widget.RecyclerView {
    public static final String TAG = RecyclerView.class.getSimpleName();

    private boolean mIsRefreshing;
    private OnLoadMoreListener mOnLoadMoreListener;

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnTouchListener((v, event) -> {
            if (mIsRefreshing) {
                return true;
            } else {
                return false;
            }
        });
    }

    public boolean isRefreshing() {
        return mIsRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        mIsRefreshing = refreshing;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (mOnLoadMoreListener == null) return;
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager.getChildCount() > 0) {
            View lastChildView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
            int lastPosition = layoutManager.getPosition(lastChildView);
            if (state == SCROLL_STATE_IDLE &&
                    lastPosition == layoutManager.getChildCount() - 1) {
                if (getAdapter().getItemCount() > 0) {
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        }
    }
}
